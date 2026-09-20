package com.mentalhealth.project.system.crisis.mapper;

import com.mentalhealth.project.system.crisis.domain.CrisisKeyword;
import java.util.List;

/**
 * 危机敏感词Mapper
 *
 */
public interface CrisisKeywordMapper
{
    /** 查询全部启用的敏感词 */
    public List<CrisisKeyword> selectAllActiveKeywords();

    /** 按ID查询 */
    public CrisisKeyword selectKeywordById(Long id);
}
