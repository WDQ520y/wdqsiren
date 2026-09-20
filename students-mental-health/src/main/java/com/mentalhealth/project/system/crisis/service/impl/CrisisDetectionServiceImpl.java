package com.mentalhealth.project.system.crisis.service.impl;

import com.mentalhealth.project.system.crisis.domain.CrisisAlertLog;
import com.mentalhealth.project.system.crisis.domain.CrisisDetectionResult;
import com.mentalhealth.project.system.crisis.domain.CrisisKeyword;
import com.mentalhealth.project.system.crisis.mapper.CrisisAlertLogMapper;
import com.mentalhealth.project.system.crisis.mapper.CrisisKeywordMapper;
import com.mentalhealth.project.system.crisis.service.ICrisisDetectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 危机检测服务：启动时加载敏感词到内存，上下文消歧降权，打分判定高危/中危，频次控制防弹窗轰炸。
 */
@Service
public class CrisisDetectionServiceImpl implements ICrisisDetectionService
{
    private static final Logger log = LoggerFactory.getLogger(CrisisDetectionServiceImpl.class);

    /** 上下文消歧词（出现则降权，可能是非危机讨论） */
    private static final String[] DISAMBIGUATION_WORDS = {
            "新闻", "论文", "小说", "电影", "电视剧", "朋友说", "他说", "她说",
            "写作业", "采访", "报道", "案例分析", "文献", "书中", "歌词", "歌词里"
    };

    /** 频次控制：N分钟内最多弹几次 */
    private static final int RATE_LIMIT_WINDOW_MIN = 10;
    private static final int RATE_LIMIT_MAX_HITS = 3;

    /** 高危阈值 */
    private static final int HIGH_THRESHOLD = 10;
    private static final int MEDIUM_THRESHOLD = 5;

    @Autowired
    private CrisisKeywordMapper keywordMapper;

    @Autowired
    private CrisisAlertLogMapper alertLogMapper;

    /** 内存缓存的敏感词列表 */
    private volatile List<CrisisKeyword> cachedKeywords = new ArrayList<>();

    @PostConstruct
    public void initCache() {
        reloadCache();
    }

    /** 管理员修改词表后可调用此方法刷新缓存 */
    public void reloadCache() {
        try {
            cachedKeywords = keywordMapper.selectAllActiveKeywords();
            log.info("危机敏感词缓存加载完成，共 {} 条", cachedKeywords.size());
        } catch (Exception e) {
            log.error("加载危机敏感词缓存失败", e);
            cachedKeywords = new ArrayList<>();
        }
    }

    @Override
    public CrisisDetectionResult detect(String content, Long userId, String scene)
    {
        if (content == null || content.trim().isEmpty()) {
            return CrisisDetectionResult.noHit();
        }
        if (cachedKeywords == null || cachedKeywords.isEmpty()) {
            reloadCache();
        }
        String text = content.toLowerCase();
        int score = 0;
        CrisisKeyword highestHit = null;
        long hitKeywordId = 0;

        // 1. 逐词匹配打分
        for (CrisisKeyword kw : cachedKeywords) {
            boolean matched = matchKeyword(text, kw);
            if (!matched) continue;

            if ("HIGH".equals(kw.getLevel())) {
                score += 10;
            } else {
                score += 5;
            }
            if (highestHit == null || "HIGH".equals(kw.getLevel())) {
                highestHit = kw;
                hitKeywordId = kw.getId();
            }
        }

        // 2. 上下文消歧：非危机讨论降权
        int disambigHits = 0;
        for (String dw : DISAMBIGUATION_WORDS) {
            if (text.contains(dw.toLowerCase())) {
                disambigHits++;
            }
        }
        score -= disambigHits * 3;

        // 3. 判定等级
        String level;
        boolean shouldBlock;
        if (score >= HIGH_THRESHOLD) {
            level = "HIGH";
            shouldBlock = true;
        } else if (score >= MEDIUM_THRESHOLD) {
            level = "MEDIUM";
            shouldBlock = "BLOCK".equals(highestHit != null ? highestHit.getAction() : "WARN");
        } else {
            return CrisisDetectionResult.noHit();
        }

        // 4. 频次控制
        int recent = alertLogMapper.countRecentHits(userId, RATE_LIMIT_WINDOW_MIN);
        boolean suppressPopup = recent >= RATE_LIMIT_MAX_HITS;

        // 5. 写命中日志（不存原文）
        String actionTaken = shouldBlock ? "BLOCKED" : "WARNED";
        if (suppressPopup) actionTaken = "SUPPRESSED_" + actionTaken;
        writeLog(userId, level, hitKeywordId, scene, null, actionTaken);

        // 6. 返回结果
        String templateId = "HIGH".equals(level) ? "HIGH" : "MEDIUM";
        if (shouldBlock) {
            // 高危始终拦截，即使频次超限也不落库
            if (suppressPopup) {
                return CrisisDetectionResult.blockedNoPopup(level, templateId);
            }
            return CrisisDetectionResult.blocked(level, templateId);
        } else {
            if (suppressPopup) {
                return CrisisDetectionResult.warnedNoPopup(level, templateId);
            }
            return CrisisDetectionResult.warned(level, templateId);
        }
    }

    /** 单条关键词匹配 */
    private boolean matchKeyword(String text, CrisisKeyword kw) {
        String k = kw.getKeyword().toLowerCase();
        if ("EXACT".equals(kw.getMatchMode())) {
            return text.equals(k);
        } else if ("REGEX".equals(kw.getMatchMode())) {
            try {
                return Pattern.compile(k).matcher(text).find();
            } catch (Exception e) {
                return false;
            }
        } else {
            // CONTAIN 默认
            return text.contains(k);
        }
    }

    /** 写日志（不存原文） */
    private void writeLog(Long userId, String level, Long keywordId,
                          String scene, Long refId, String actionTaken) {
        try {
            CrisisAlertLog logEntry = new CrisisAlertLog();
            logEntry.setUserId(userId);
            logEntry.setLevel(level);
            logEntry.setKeywordId(keywordId);
            logEntry.setScene(scene);
            logEntry.setRefId(refId);
            logEntry.setActionTaken(actionTaken);
            alertLogMapper.insertAlertLog(logEntry);
        } catch (Exception e) {
            log.error("写入危机命中日志失败", e);
        }
    }
}
