package com.mentalhealth.project.system.ai.service.impl;

import com.mentalhealth.project.system.ai.domain.AiChatMessage;
import com.mentalhealth.project.system.ai.domain.AiKnowledge;
import com.mentalhealth.project.system.ai.mapper.AiChatMapper;
import com.mentalhealth.project.system.ai.mapper.AiKnowledgeMapper;
import com.mentalhealth.project.system.ai.mapper.AiMentalKnowledgeMapper;
import com.mentalhealth.project.system.ai.service.IAiChatService;
import com.mentalhealth.project.system.crisis.service.ICrisisDetectionService;
import com.mentalhealth.project.system.crisis.domain.CrisisDetectionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class AiChatServiceImpl implements IAiChatService {

    @Autowired
    private AiChatMapper chatMapper;

    @Autowired
    private AiKnowledgeMapper knowledgeMapper;

    @Autowired
    private AiMentalKnowledgeMapper mentalKnowledgeMapper;

    @Autowired
    private ICrisisDetectionService crisisDetectionService;

    // 情绪词典
    private static final Map<String, List<String>> EMOTION_LEXICON = new LinkedHashMap<>();
    static {
        EMOTION_LEXICON.put("焦虑", Arrays.asList("焦虑,紧张,担心,害怕,恐慌,不安,心慌,压力,喘不过气,坐立不安,忧虑,忐忑,着急,慌".split(",")));
        EMOTION_LEXICON.put("抑郁", Arrays.asList("抑郁,难过,沮丧,低落,无助,绝望,空虚,没意思,不想动,疲惫,自卑,自责,孤独,伤心,想哭,失恋,分手,难过".split(",")));
        EMOTION_LEXICON.put("愤怒", Arrays.asList("生气,愤怒,恼火,烦躁,讨厌,烦,气愤,暴怒,不公平,气死我了,讨厌".split(",")));
        EMOTION_LEXICON.put("平静", Arrays.asList("还好,没事,正常,平静,稳定,可以,还行,一般".split(",")));
        EMOTION_LEXICON.put("压力", Arrays.asList("压力,扛不住,崩溃,撑不住,累,作业,考试,论文,毕业,就业,面试,挂科,复习".split(",")));
    }

    // 共情回复模板
    private static final Map<String, String> EMPATHY_RESPONSES = new LinkedHashMap<>();
    static {
        EMPATHY_RESPONSES.put("焦虑", "听起来你现在心里七上八下的，这种悬着的感觉确实不好受。能和我说说具体是什么事让你这么紧张吗？我们可以一起看看，把模糊的担忧变成具体的问题，或许就没那么可怕了。");
        EMPATHY_RESPONSES.put("抑郁", "我能感觉到你现在心里沉甸甸的。谢谢你愿意把这些说出来——这本身就需要勇气。情绪低落的时候，好像什么都提不起劲，这不是你的错。不用急着好起来，我们慢慢来。你想多聊聊吗？");
        EMPATHY_RESPONSES.put("愤怒", "听到这件事你这么生气，换谁都会有情绪的。愤怒其实说明你很在意这件事。先做个深呼吸，把事情的来龙去脉和我说说，我在这里听着。");
        EMPATHY_RESPONSES.put("压力", "最近好像事情堆在一起了，换谁都容易觉得扛不住。不用要求自己一下子搞定所有事。我们可以试着把任务拆成一小块一小块，先挑最容易的开始。你现在最想先处理哪件？");
        EMPATHY_RESPONSES.put("平静", "嗯，能保持平稳的状态也挺好的。如果之后有什么想聊的，不管是开心的还是烦恼的，我都在。");
    }

    // 话题特定回复
    private static final Map<String, String[]> TOPIC_RESPONSES = new LinkedHashMap<>();
    static {
        TOPIC_RESPONSES.put("失恋|分手|分手了", new String[]{
            "失恋真的很痛，那种感觉就像心里被挖空了一块。允许自己难过，不用急着走出来。能和我说说你们是怎么结束的吗？",
            "听到这个消息，我想抱抱你。分手后的痛苦是正常的，说明你真的投入过。这段时间对自己好一点，好好吃饭睡觉，想说话的时候我一直都在。"
        });
        TOPIC_RESPONSES.put("考试|挂科|复习|学习", new String[]{
            "考试压力确实很大。你现在是在复习哪门课？试试番茄工作法：专注25分钟然后休息5分钟，比硬扛一整天效率高很多。",
            "面对考试紧张是正常的。与其担心结果，不如把注意力放在'现在能做的一道题上'。你觉得最难的部分是什么？"
        });
        TOPIC_RESPONSES.put("孤独|没人陪|一个人", new String[]{
            "一个人的时候确实容易觉得空落落的。这种感觉不是因为你不好，只是人本来就需要连接。要不要试试参加点社团活动，或者找信任的朋友聊聊？",
            "孤独的时候，好像全世界都在热闹只有自己安静着。这种时候可以试试做点自己喜欢的事——听听音乐、看看电影，或者来找我聊聊，我一直都在。"
        });
        TOPIC_RESPONSES.put("想家|妈妈|爸爸|父母", new String[]{
            "想家了说明你和家人的连接很深。这种时候给家里打个电话吧，不用聊什么大事，哪怕只是说说今天吃了什么。",
            "离开家之后才发现，原来那些平常的日子这么珍贵。你有多久没和家里联系了？也许现在就是个合适的时机。"
        });
    }

    // 通用问答
    private static final Map<String, String> FAQ = new LinkedHashMap<>();
    static {
        FAQ.put("你好|hi|hello|在吗|嗨", "你好呀！我是你的AI心理陪伴助手。我不是专业医生，但我会认真听你说，陪你聊聊心情和困扰。今天想聊点什么呢？");
        FAQ.put("你是谁|你叫什么", "我是AI心理陪伴助手，可以陪你聊聊情绪、压力、人际关系等话题。我会尽力理解你的感受，也会基于心理学知识给你一些建议。不过要记得，我不是医生，不能替代专业心理咨询哦。");
        FAQ.put("谢谢|感谢|thank", "不客气！能陪你聊这些我很开心。记得照顾好自己，有需要随时来找我。");
        FAQ.put("再见|拜拜|bye", "再见！希望你今天能过得轻松一些。如果之后需要聊聊，我一直都在。记得：你值得被好好对待。");
        FAQ.put("怎么办|我该怎么办", "当我们不知道该怎么办的时候，先停下来深呼吸是个好办法。你能和我说说具体是什么情况吗？了解更多之后，我们可以一起想想可以从哪里开始。");
        FAQ.put("睡不着|失眠|睡不好", "失眠往往和脑子里想太多事情有关。试试：1）睡前1小时放下手机 2）试试4-7-8呼吸法 3）如果脑子停不下来，把想法写在纸上。如果长期失眠，建议去校医院看看哦。");
    }

    @Override
    public Map<String, Object> chat(Long userId, String message) {
        Map<String, Object> result = new HashMap<>();

        // 1. 情绪识别
        String emotion = detectEmotion(message);

        // 2. 风险词表优先匹配
        Map<String, Object> riskHit = mentalKnowledgeMapper.searchRiskWord(message);
        if (riskHit != null) {
            String reply = (String) riskHit.get("reply_template");
            String level = (String) riskHit.get("level");
            // 保存用户消息
            AiChatMessage userMsg = new AiChatMessage();
            userMsg.setUserId(userId);
            userMsg.setRole("user");
            userMsg.setContent(message);
            userMsg.setEmotion(emotion);
            userMsg.setCrisisLevel(level);
            chatMapper.insertMessage(userMsg);
            // 保存AI回复
            AiChatMessage aiMsg = new AiChatMessage();
            aiMsg.setUserId(userId);
            aiMsg.setRole("assistant");
            aiMsg.setContent(reply);
            aiMsg.setEmotion(emotion);
            chatMapper.insertMessage(aiMsg);
            result.put("reply", reply);
            result.put("emotion", emotion);
            if ("高".equals(level)) result.put("crisis", "HIGH");
            else result.put("crisis", "MEDIUM");
            return result;
        }

        // 3. 危机检测（通用敏感词）
        CrisisDetectionResult crisis = crisisDetectionService.detect(message, userId, "AI_CHAT");
        String crisisLevel = crisis.isHit() ? (crisis.getLevel() != null ? crisis.getLevel() : "HIGH") : null;

        // 4. 保存用户消息
        AiChatMessage userMsg = new AiChatMessage();
        userMsg.setUserId(userId);
        userMsg.setRole("user");
        userMsg.setContent(message);
        userMsg.setEmotion(emotion);
        userMsg.setCrisisLevel(crisisLevel);
        chatMapper.insertMessage(userMsg);

        // 5. 生成AI回复
        String aiReply;
        if (crisis.isHit() && crisis.isBlocked()) {
            aiReply = "我很担心你现在的状态。你不需要一个人扛着。请先联系你信任的人，或者拨打全国心理援助热线 12356。如果你现在有立即伤害自己的危险，请拨打 110 或 120。你现在是否在一个安全的地方？";
            result.put("crisis", "HIGH");
        } else {
        // 6. 知识库匹配
        List<Map<String, Object>> allKb = mentalKnowledgeMapper.selectAllKnowledge();
        Map<String, Object> bestKb = null;
        int bestPriority = -1;
        for (Map<String, Object> kb : allKb) {
            String keywords = (String) kb.get("keywords");
            if (keywords == null) continue;
            for (String kw : keywords.split(",")) {
                kw = kw.trim();
                if (!kw.isEmpty() && message.contains(kw)) {
                    int pri = kb.get("priority") != null ? ((Number) kb.get("priority")).intValue() : 0;
                    if (pri > bestPriority) {
                        bestPriority = pri;
                        bestKb = kb;
                    }
                    break;
                }
            }
        }
        if (bestKb != null) {
            aiReply = (String) bestKb.get("answer");
            if ("高".equals(bestKb.get("risk_level"))) result.put("crisis", "HIGH");
        } else {
            aiReply = generateReply(message, emotion, null);
        }
        }

        // 7. 保存AI回复
        AiChatMessage aiMsg = new AiChatMessage();
        aiMsg.setUserId(userId);
        aiMsg.setRole("assistant");
        aiMsg.setContent(aiReply);
        aiMsg.setEmotion(emotion);
        chatMapper.insertMessage(aiMsg);

        result.put("reply", aiReply);
        result.put("emotion", emotion);
        return result;
    }

    private String generateReply(String message, String emotion, List<Map<String, Object>> knowledges) {
        // 先匹配FAQ
        for (Map.Entry<String, String> entry : FAQ.entrySet()) {
            if (Pattern.compile(entry.getKey()).matcher(message).find()) {
                return entry.getValue();
            }
        }

        // 话题特定回复
        for (Map.Entry<String, String[]> entry : TOPIC_RESPONSES.entrySet()) {
            if (Pattern.compile(entry.getKey()).matcher(message).find()) {
                String[] replies = entry.getValue();
                return replies[new Random().nextInt(replies.length)];
            }
        }

        // 知识库匹配到了直接用
        if (knowledges != null && !knowledges.isEmpty()) {
            return (String) knowledges.get(0).get("answer");
        }

        // 情绪共情
        StringBuilder sb = new StringBuilder();
        String empathy = EMPATHY_RESPONSES.get(emotion);
        if (empathy != null && !"平静".equals(emotion)) {
            sb.append(empathy);
        } else {
            String[] generic = {
                "嗯，我在听。能和我多说一点吗？",
                "我理解你的感受。后来呢？",
                "谢谢你和我说这些。具体是什么情况？",
                "我在认真听。这件事对你影响大吗？"
            };
            sb.append(generic[new Random().nextInt(generic.length)]);
        }
        return sb.toString();
    }

    private void appendKnowledge(List<AiKnowledge> knowledges, StringBuilder sb) {
        if (knowledges != null && !knowledges.isEmpty()) {
            for (AiKnowledge k : knowledges) {
                sb.append("💡 ").append(k.getContent()).append("\n\n");
            }
        }
    }

    @Override
    public String detectEmotion(String text) {
        int maxScore = 0;
        String bestEmotion = "平静";
        for (Map.Entry<String, List<String>> entry : EMOTION_LEXICON.entrySet()) {
            int score = 0;
            for (String keyword : entry.getValue()) {
                if (text.contains(keyword)) score++;
            }
            if (score > maxScore) {
                maxScore = score;
                bestEmotion = entry.getKey();
            }
        }
        return bestEmotion;
    }

    @Override
    public List<AiKnowledge> retrieveKnowledge(String query) {
        // 简单关键词检索：把查询文本拆成关键词匹配知识库
        List<AiKnowledge> all = knowledgeMapper.selectAllEnabled();
        List<AiKnowledge> matched = new ArrayList<>();
        for (AiKnowledge k : all) {
            String kw = k.getKeywords() == null ? "" : k.getKeywords();
            String title = k.getTitle() == null ? "" : k.getTitle();
            String content = k.getContent() == null ? "" : k.getContent();
            for (String keyword : kw.split(",")) {
                if (!keyword.trim().isEmpty() && query.contains(keyword.trim())) {
                    matched.add(k);
                    break;
                }
            }
            if (matched.size() >= 2) break;
        }
        return matched;
    }

    @Override
    public List<AiChatMessage> getRecentChat(Long userId) {
        return chatMapper.selectRecentMessages(userId, 20);
    }

    @Override
    public void clearChat(Long userId) {
        chatMapper.deleteByUserId(userId);
    }
}
