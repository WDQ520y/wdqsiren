package com.mentalhealth.project.system.ai.mapper;

import com.mentalhealth.project.system.ai.domain.AiChatMessage;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface AiChatMapper {
    void insertMessage(AiChatMessage message);
    List<AiChatMessage> selectRecentMessages(@Param("userId") Long userId, @Param("limit") int limit);
    List<AiChatMessage> selectAllMessages(@Param("userId") Long userId, @Param("emotion") String emotion);
    List<Map<String,Object>> emotionStats();
    void deleteByUserId(@Param("userId") Long userId);
}
