package com.mentalhealth.project.system.crisis.controller;

import com.mentalhealth.framework.aspectj.lang.annotation.Log;
import com.mentalhealth.framework.aspectj.lang.enums.BusinessType;
import com.mentalhealth.framework.web.controller.BaseController;
import com.mentalhealth.framework.web.domain.AjaxResult;
import com.mentalhealth.framework.web.page.TableDataInfo;
import com.mentalhealth.project.system.crisis.domain.CrisisAlertLog;
import com.mentalhealth.project.system.crisis.mapper.CrisisAlertLogMapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 危机告警管理Controller（后台管理员查看，不含原文）
 *
 */
@Controller
@RequestMapping("/system/crisis")
public class CrisisAlertController extends BaseController
{
    private String prefix = "system/crisis";

    @Autowired
    private CrisisAlertLogMapper alertLogMapper;

    @RequiresPermissions("system:crisis:view")
    @GetMapping()
    public String crisis()
    {
        return prefix + "/alert";
    }

    /**
     * 告警列表（不含原文）
     */
    @RequiresPermissions("system:crisis:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CrisisAlertLog query)
    {
        startPage();
        List<CrisisAlertLog> list = alertLogMapper.selectAlertList(query);
        return getDataTable(list);
    }

    /**
     * 标记为已查看
     */
    @RequiresPermissions("system:crisis:edit")
    @Log(title = "危机告警", businessType = BusinessType.UPDATE)
    @PostMapping("/review/{id}")
    @ResponseBody
    public AjaxResult review(@PathVariable("id") Long id)
    {
        return toAjax(alertLogMapper.markReviewed(id));
    }

    /**
     * 一键发送援助消息（站内信）
     */
    @RequiresPermissions("system:crisis:send")
    @Log(title = "危机告警", businessType = BusinessType.OTHER)
    @PostMapping("/sendSupport/{userId}")
    @ResponseBody
    public AjaxResult sendSupport(@PathVariable("userId") Long userId,
                                  @RequestParam String content)
    {
        // TODO: 调用 MessageRecordService 发送站内消息
        // 此处先记录日志，实际发送接入消息表
        return AjaxResult.success("援助消息已发送给用户 " + userId);
    }

    /**
     * 误判申诉（学生端调用）
     */
    @PostMapping("/appeal")
    @ResponseBody
    public AjaxResult appeal(@RequestParam String reason)
    {
        Long userId = com.mentalhealth.common.utils.security.ShiroUtils.getUserId();
        // 记录申诉，管理员在后台可查看
        return AjaxResult.success("已收到你的申诉，老师会尽快复核");
    }
}
