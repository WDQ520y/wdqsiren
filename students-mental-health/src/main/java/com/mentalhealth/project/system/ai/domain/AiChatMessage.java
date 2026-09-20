package com.mentalhealth.project.system.ai.domain;

import java.util.Date;

public class AiChatMessage {
    private Long id;
    private Long userId;
    private String role;
    private String content;
    private String emotion;
    private String crisisLevel;
    private Date createTime;
    private String userName;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getEmotion() { return emotion; }
    public void setEmotion(String emotion) { this.emotion = emotion; }
    public String getCrisisLevel() { return crisisLevel; }
    public void setCrisisLevel(String crisisLevel) { this.crisisLevel = crisisLevel; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
}
