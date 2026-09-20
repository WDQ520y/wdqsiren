package com.mentalhealth.project.system.crisis.domain;

import com.mentalhealth.framework.aspectj.lang.annotation.Excel;
import com.mentalhealth.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 危机敏感词对象 crisis_keyword
 *
 * @date 2026-09-19
 */
public class CrisisKeyword extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    /** 关键词 */
    @Excel(name = "关键词")
    private String keyword;

    /** 危机等级 HIGH/MEDIUM */
    @Excel(name = "危机等级")
    private String level;

    /** 匹配模式 EXACT/CONTAIN/REGEX */
    @Excel(name = "匹配模式")
    private String matchMode;

    /** 命中动作 BLOCK/WARN */
    @Excel(name = "命中动作")
    private String action;

    /** 所属分类 */
    @Excel(name = "所属分类")
    private String category;

    /** 状态 0正常 1停用 */
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public String getMatchMode() { return matchMode; }
    public void setMatchMode(String matchMode) { this.matchMode = matchMode; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("keyword", getKeyword())
                .append("level", getLevel())
                .append("matchMode", getMatchMode())
                .append("action", getAction())
                .append("category", getCategory())
                .append("status", getStatus())
                .toString();
    }
}
