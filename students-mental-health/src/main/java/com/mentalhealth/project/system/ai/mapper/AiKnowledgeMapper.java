package com.mentalhealth.project.system.ai.mapper;

import com.mentalhealth.project.system.ai.domain.AiKnowledge;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface AiKnowledgeMapper {
    List<AiKnowledge> selectList(AiKnowledge query);
    AiKnowledge selectById(@Param("id") Long id);
    void insertKnowledge(AiKnowledge knowledge);
    void updateKnowledge(AiKnowledge knowledge);
    void deleteById(@Param("id") Long id);
    List<AiKnowledge> searchByKeywords(@Param("keywords") String keywords);
    List<AiKnowledge> selectAllEnabled();
}
