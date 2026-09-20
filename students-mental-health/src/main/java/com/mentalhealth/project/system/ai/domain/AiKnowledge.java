package com.mentalhealth.project.system.ai.domain;

public class AiKnowledge {
    private Long id;
    private String title;
    private String category;
    private String content;
    private String keywords;
    private String status;
    private String createTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getKeywords() { return keywords; }
    public void setKeywords(String keywords) { this.keywords = keywords; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCreateTime() { return createTime; }
    public void setCreateTime(String createTime) { this.createTime = createTime; }
}
