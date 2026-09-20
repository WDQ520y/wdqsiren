package com.mentalhealth.project.system.crisis.mapper;

import com.mentalhealth.project.system.crisis.domain.CrisisAlertLog;
import java.util.List;

/**
 * 危机命中日志Mapper
 *
 */
public interface CrisisAlertLogMapper
{
    /** 插入命中日志（不存原文） */
    public int insertAlertLog(CrisisAlertLog log);

    /** 查询用户在最近N分钟内的命中次数（用于频次控制） */
    public int countRecentHits(Long userId, int minutes);

    /** 后台告警列表（JOIN用户表取用户名，不含原文） */
    public List<CrisisAlertLog> selectAlertList(CrisisAlertLog query);

    /** 标记为已查看 */
    public int markReviewed(Long id);
}
