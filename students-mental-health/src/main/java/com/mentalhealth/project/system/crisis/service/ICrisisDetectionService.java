package com.mentalhealth.project.system.crisis.service;

import com.mentalhealth.project.system.crisis.domain.CrisisDetectionResult;

/**
 * 危机检测服务接口
 *
 */
public interface ICrisisDetectionService
{
    /**
     * 检测文本是否包含危机信号
     *
     * @param content 待检测文本（帖子正文/评论内容）
     * @param userId  触发用户ID
     * @param scene   场景：POST 或 COMMENT
     * @return 检测结果（等级、是否拦截、命中词、提示文案等）
     */
    CrisisDetectionResult detect(String content, Long userId, String scene);
}
