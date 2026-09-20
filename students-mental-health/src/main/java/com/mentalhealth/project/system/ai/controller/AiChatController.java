package com.mentalhealth.project.system.ai.controller;

import com.mentalhealth.project.system.ai.domain.AiChatMessage;
import com.mentalhealth.project.system.ai.domain.AiKnowledge;
import com.mentalhealth.project.system.ai.mapper.AiChatMapper;
import com.mentalhealth.project.system.ai.mapper.AiKnowledgeMapper;
import com.mentalhealth.project.system.ai.service.IAiChatService;
import com.mentalhealth.framework.web.controller.BaseController;
import com.mentalhealth.framework.web.domain.AjaxResult;
import com.mentalhealth.framework.web.page.TableDataInfo;
import com.mentalhealth.common.utils.security.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/ai")
public class AiChatController extends BaseController {

    @Autowired
    private IAiChatService aiChatService;

    @Autowired
    private AiChatMapper chatMapper;

    @Autowired
    private AiKnowledgeMapper knowledgeMapper;

    /** 前台聊天页面 */
    @GetMapping("/chat")
    public String chatPage(ModelMap mp) {
        Long userId = getSysUserId();
        List<AiChatMessage> history = aiChatService.getRecentChat(userId);
        Collections.reverse(history);
        mp.put("history", history);
        mp.put("user", getSysUser());
        return "foreground/client/ai/chat";
    }

    /** 发送消息 */
    @PostMapping("/send")
    @ResponseBody
    public AjaxResult send(@RequestBody Map<String, String> body) {
        Long userId = getSysUserId();
        String message = body.get("message");
        if (message == null || message.trim().isEmpty()) {
            return AjaxResult.error("消息不能为空");
        }
        Map<String, Object> result = aiChatService.chat(userId, message);
        return AjaxResult.success(result);
    }

    /** 清空对话 */
    @PostMapping("/clear")
    @ResponseBody
    public AjaxResult clear() {
        aiChatService.clearChat(getSysUserId());
        return AjaxResult.success();
    }

    // ===== 后台管理 =====

    /** 知识库管理页 */
    @GetMapping("/knowledge")
    public String knowledgePage() {
        return "system/ai/knowledge";
    }

    @PostMapping("/knowledge/list")
    @ResponseBody
    public TableDataInfo knowledgeList(AiKnowledge query) {
        startPage();
        List<AiKnowledge> list = knowledgeMapper.selectList(query);
        return getDataTable(list);
    }

    @PostMapping("/knowledge/add")
    @ResponseBody
    public AjaxResult knowledgeAdd(AiKnowledge knowledge) {
        knowledgeMapper.insertKnowledge(knowledge);
        return AjaxResult.success();
    }

    @PostMapping("/knowledge/edit")
    @ResponseBody
    public AjaxResult knowledgeEdit(AiKnowledge knowledge) {
        knowledgeMapper.updateKnowledge(knowledge);
        return AjaxResult.success();
    }

    @PostMapping("/knowledge/delete/{id}")
    @ResponseBody
    public AjaxResult knowledgeDelete(@PathVariable Long id) {
        knowledgeMapper.deleteById(id);
        return AjaxResult.success();
    }

    /** 对话记录页 */
    @GetMapping("/records")
    public String recordsPage(ModelMap mp) {
        List<AiChatMessage> list = chatMapper.selectAllMessages(null, null);
        mp.put("records", list);
        return "system/ai/records";
    }

    @PostMapping("/records/list")
    @ResponseBody
    public TableDataInfo recordsList(AiChatMessage query) {
        startPage();
        List<AiChatMessage> list = chatMapper.selectAllMessages(query.getUserId(), query.getEmotion());
        return getDataTable(list);
    }

    /** 情绪统计页 */
    @GetMapping("/stats")
    public String statsPage(ModelMap mp) {
        List<Map<String, Object>> stats = chatMapper.emotionStats();
        mp.put("stats", stats);
        return "system/ai/stats";
    }

    private Long getSysUserId() {
        return getSysUser().getUserId();
    }
}
