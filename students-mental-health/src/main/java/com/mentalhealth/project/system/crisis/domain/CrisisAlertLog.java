package com.mentalhealth.project.system.crisis.domain;

import com.mentalhealth.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 危机命中日志对象 crisis_alert_log（不存原文）
 *
 * @date 2026-09-19
 */
public class CrisisAlertLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    /** 触发用户ID */
    private Long userId;

    /** 命中等级 HIGH/MEDIUM */
    private String level;

    /** 命中关键词ID */
    private Long keywordId;

    /** 场景 POST/COMMENT */
    private String scene;

    /** 关联帖子/评论ID */
    private Long refId;

    /** 实际处置 BLOCKED/WARNED */
    private String actionTaken;

    /** 是否已查看 */
    private Integer reviewed;

    /** 冗余：用户名（后台列表展示用） */
    private String userName;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public Long getKeywordId() { return keywordId; }
    public void setKeywordId(Long keywordId) { this.keywordId = keywordId; }
    public String getScene() { return scene; }
    public void setScene(String scene) { this.scene = scene; }
    public Long getRefId() { return refId; }
    public void setRefId(Long refId) { this.refId = refId; }
    public String getActionTaken() { return actionTaken; }
    public void setActionTaken(String actionTaken) { this.actionTaken = actionTaken; }
    public Integer getReviewed() { return reviewed; }
    public void setReviewed(Integer reviewed) { this.reviewed = reviewed; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
}
