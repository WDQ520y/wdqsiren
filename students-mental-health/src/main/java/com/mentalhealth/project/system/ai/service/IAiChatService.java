package com.mentalhealth.project.system.ai.service;

import com.mentalhealth.project.system.ai.domain.AiChatMessage;
import com.mentalhealth.project.system.ai.domain.AiKnowledge;
import java.util.List;
import java.util.Map;

public interface IAiChatService {
    /** 发送消息，返回AI回复 */
    Map<String, Object> chat(Long userId, String message);
    /** 获取最近对话 */
    List<AiChatMessage> getRecentChat(Long userId);
    /** 清空对话 */
    void clearChat(Long userId);
    /** 情绪识别 */
    String detectEmotion(String text);
    /** 知识库检索 */
    List<AiKnowledge> retrieveKnowledge(String query);
}
