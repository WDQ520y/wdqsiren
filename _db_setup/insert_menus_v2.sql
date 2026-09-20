-- 心理健康管理系统菜单重新组织
USE mental_health;

-- 1. 删除旧菜单
DELETE FROM sys_role_menu WHERE menu_id >= 2000 AND menu_id < 2200;
DELETE FROM sys_menu WHERE menu_id >= 2000 AND menu_id < 2200;

-- 2. 基础数据管理 (menu_id=3000)
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3000, '基础数据管理', 0, 1, '#', '', 'M', '0', '1', '', 'system', 'admin', NOW(), '基础数据管理');

-- 将现有用户/角色/菜单/部门管理移到基础数据管理下
UPDATE sys_menu SET parent_id=3000 WHERE menu_id IN (100,101,102,103);
UPDATE sys_menu SET order_num=1 WHERE menu_id=100;
UPDATE sys_menu SET order_num=2 WHERE menu_id=101;
UPDATE sys_menu SET order_num=3 WHERE menu_id=102;
UPDATE sys_menu SET order_num=4 WHERE menu_id=103;

-- 学生信息管理
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3001, '学生信息管理', 3000, 5, '/system/student', '', 'C', '0', '1', 'system:student:list', 'user', 'admin', NOW(), '学生信息管理');

-- 专业信息管理
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3002, '专业信息管理', 3000, 6, '/system/major', '', 'C', '0', '1', 'system:major:list', 'dict', 'admin', NOW(), '专业信息管理');

-- 班级信息
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3003, '班级信息', 3000, 7, '/system/classes', '', 'C', '0', '1', 'system:classes:list', 'tree', 'admin', NOW(), '班级信息');

-- 3. 业务信息管理 (menu_id=3100)
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3100, '业务信息管理', 0, 2, '#', '', 'M', '0', '1', '', 'example', 'admin', NOW(), '业务信息管理');

-- 心理测评信息管理 (menu_id=3101)
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3101, '心理测评信息管理', 3100, 1, '#', '', 'M', '0', '1', '', 'chart', 'admin', NOW(), '心理测评信息管理');

-- 心理测评量表管理
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3102, '心理测评量表管理', 3101, 1, '/system/scale', '', 'C', '0', '1', 'system:scale:list', 'clipboard', 'admin', NOW(), '心理测评量表管理');

-- 测评量表问题信息
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3103, '测评量表问题信息', 3101, 2, '/system/question', '', 'C', '0', '1', 'system:question:list', 'edit', 'admin', NOW(), '测评量表问题信息');

-- 问题选项信息
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3104, '问题选项信息', 3101, 3, '/system/question_answer', '', 'C', '0', '1', 'system:questionAnswer:list', 'list', 'admin', NOW(), '问题选项信息');

-- 测评因子信息
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3105, '测评因子信息', 3101, 4, '/system/factor', '', 'C', '0', '1', 'system:factor:list', 'list', 'admin', NOW(), '测评因子信息');

-- 测评计算公式
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3106, '测评计算公式', 3101, 5, '/system/formula', '', 'C', '0', '1', 'system:formula:list', 'calculation', 'admin', NOW(), '测评计算公式');

-- 测评结果参考信息
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3107, '测评结果参考信息', 3101, 6, '/system/result', '', 'C', '0', '1', 'system:result:list', 'form', 'admin', NOW(), '测评结果参考信息');

-- 测评成绩报告信息 (menu_id=3108)
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3108, '测评成绩报告信息', 3101, 7, '#', '', 'M', '0', '1', '', 'file', 'admin', NOW(), '测评成绩报告信息');

-- 个人测评成绩报告
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3109, '个人测评成绩报告', 3108, 1, '/system/result_personal', '', 'C', '0', '1', 'system:resultPersonal:list', 'user', 'admin', NOW(), '个人测评成绩报告');

-- 心理健康知识管理 (menu_id=3110)
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3110, '心理健康知识管理', 3100, 2, '#', '', 'M', '0', '1', '', 'documentation', 'admin', NOW(), '心理健康知识管理');

-- 心理健康知识
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3111, '心理健康知识', 3110, 1, '/system/knowledge', '', 'C', '0', '1', 'system:knowledge:list', 'documentation', 'admin', NOW(), '心理健康知识');

-- 心理健康知识审核
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3112, '心理健康知识审核', 3110, 2, '/system/knowledge_examine', '', 'C', '0', '1', 'system:knowledgeExamine:list', 'check', 'admin', NOW(), '心理健康知识审核');

-- 学生收藏信息
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3113, '学生收藏信息', 3110, 3, '/system/knowledge_collection', '', 'C', '0', '1', 'system:knowledgeCollection:list', 'star', 'admin', NOW(), '学生收藏信息');

-- 心理社区信息管理 (menu_id=3114)
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3114, '心理社区信息管理', 3100, 3, '#', '', 'M', '0', '1', '', 'message', 'admin', NOW(), '心理社区信息管理');

-- 帖子信息管理
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3115, '帖子信息管理', 3114, 1, '/system/posts', '', 'C', '0', '1', 'system:posts:list', 'message', 'admin', NOW(), '帖子信息管理');

-- 个人帖子信息管理
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3116, '个人帖子信息管理', 3114, 2, '/system/posts_user', '', 'C', '0', '1', 'system:postsUser:list', 'user', 'admin', NOW(), '个人帖子信息管理');

-- 评论信息管理
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3117, '评论信息管理', 3114, 3, '/system/comment', '', 'C', '0', '1', 'system:comment:list', 'comment', 'admin', NOW(), '评论信息管理');

-- 个人评论信息管理
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3118, '个人评论信息管理', 3114, 4, '/system/comment_user', '', 'C', '0', '1', 'system:commentUser:list', 'user', 'admin', NOW(), '个人评论信息管理');

-- 举报信息
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3119, '举报信息', 3114, 5, '/system/posts?report=1', '', 'C', '0', '1', 'system:posts:report', 'warning', 'admin', NOW(), '举报信息');

-- 个人举报信息
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3120, '个人举报信息', 3114, 6, '/system/posts_user?report=1', '', 'C', '0', '1', 'system:postsUser:report', 'user', 'admin', NOW(), '个人举报信息');

-- 心理音乐信息管理 (menu_id=3121)
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3121, '心理音乐信息管理', 3100, 4, '#', '', 'M', '0', '1', '', 'music', 'admin', NOW(), '心理音乐信息管理');

-- 心理音乐
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3122, '心理音乐', 3121, 1, '/system/music', '', 'C', '0', '1', 'system:music:list', 'music', 'admin', NOW(), '心理音乐');

-- 公告管理
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (3123, '公告管理', 3100, 5, '/system/notice', '', 'C', '0', '1', 'system:notice:list', 'message', 'admin', NOW(), '公告管理');

-- 关联到管理员角色
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(1, 3000), (1, 3001), (1, 3002), (1, 3003),
(1, 3100), (1, 3101), (1, 3102), (1, 3103), (1, 3104),
(1, 3105), (1, 3106), (1, 3107), (1, 3108), (1, 3109),
(1, 3110), (1, 3111), (1, 3112), (1, 3113),
(1, 3114), (1, 3115), (1, 3116), (1, 3117), (1, 3118), (1, 3119), (1, 3120),
(1, 3121), (1, 3122), (1, 3123);
