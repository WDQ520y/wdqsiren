package com.mentalhealth.project.system.ai.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface AiMentalKnowledgeMapper {
    List<Map<String,Object>> searchKnowledge(@Param("keywordList") List<String> keywordList, @Param("text") String text);
    Map<String,Object> searchRiskWord(@Param("text") String text);
    List<Map<String,Object>> selectAllKnowledge();
}
