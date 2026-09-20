package com.mentalhealth.project.system.crisis.domain;

/**
 * 危机检测结果（返回给前端）
 *
 */
public class CrisisDetectionResult
{
    /** 是否命中 */
    private boolean hit;
    /** 命中等级 HIGH/MEDIUM */
    private String level;
    /** 是否强拦截（true=拦截不落库，false=弱提示但正常发布） */
    private boolean blocked;
    /** 弹窗文案编号（前端按此选模板） */
    private String templateId;
    /** 是否因频次控制跳过弹窗（已记录日志但不弹） */
    private boolean suppressedByRateLimit;

    public static CrisisDetectionResult noHit() {
        CrisisDetectionResult r = new CrisisDetectionResult();
        r.hit = false;
        r.blocked = false;
        return r;
    }

    public static CrisisDetectionResult blocked(String level, String templateId) {
        CrisisDetectionResult r = new CrisisDetectionResult();
        r.hit = true; r.blocked = true;
        r.level = level; r.templateId = templateId;
        return r;
    }

    public static CrisisDetectionResult warned(String level, String templateId) {
        CrisisDetectionResult r = new CrisisDetectionResult();
        r.hit = true; r.blocked = false;
        r.level = level; r.templateId = templateId;
        return r;
    }

    public static CrisisDetectionResult suppressed() {
        CrisisDetectionResult r = new CrisisDetectionResult();
        r.hit = true; r.blocked = false;
        r.suppressedByRateLimit = true;
        return r;
    }

    public static CrisisDetectionResult blockedNoPopup(String level, String templateId) {
        CrisisDetectionResult r = new CrisisDetectionResult();
        r.hit = true; r.blocked = true;
        r.level = level; r.templateId = templateId;
        r.suppressedByRateLimit = true;
        return r;
    }

    public static CrisisDetectionResult warnedNoPopup(String level, String templateId) {
        CrisisDetectionResult r = new CrisisDetectionResult();
        r.hit = true; r.blocked = false;
        r.level = level; r.templateId = templateId;
        r.suppressedByRateLimit = true;
        return r;
    }

    public boolean isHit() { return hit; }
    public boolean isBlocked() { return blocked; }
    public String getLevel() { return level; }
    public String getTemplateId() { return templateId; }
    public boolean isSuppressedByRateLimit() { return suppressedByRateLimit; }
}
