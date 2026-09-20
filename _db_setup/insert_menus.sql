-- 心理健康管理系统菜单初始化
USE mental_health;

-- 清理旧菜单
DELETE FROM sys_role_menu WHERE menu_id >= 2000 AND menu_id < 2100;
DELETE FROM sys_menu WHERE menu_id >= 2000 AND menu_id < 2100;

-- 父菜单：心理健康管理
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (2000, '心理健康管理', 0, 1, '#', '', 'M', '0', '1', '', 'heart', 'admin', NOW(), '心理健康管理目录');

-- 学生管理
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (2001, '学生管理', 2000, 1, '/system/student', '', 'C', '0', '1', 'system:student:list', 'user', 'admin', NOW(), '学生管理');

-- 班级管理
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (2002, '班级管理', 2000, 2, '/system/classes', '', 'C', '0', '1', 'system:classes:list', 'tree', 'admin', NOW(), '班级管理');

-- 专业管理
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (2003, '专业管理', 2000, 3, '/system/major', '', 'C', '0', '1', 'system:major:list', 'dict', 'admin', NOW(), '专业管理');

-- 心理知识
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (2004, '心理知识', 2000, 4, '/system/knowledge', '', 'C', '0', '1', 'system:knowledge:list', 'documentation', 'admin', NOW(), '心理知识管理');

-- 音乐管理
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (2005, '音乐管理', 2000, 5, '/system/music', '', 'C', '0', '1', 'system:music:list', 'music', 'admin', NOW(), '音乐管理');

-- 帖子管理
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (2006, '帖子管理', 2000, 6, '/system/posts', '', 'C', '0', '1', 'system:posts:list', 'message', 'admin', NOW(), '帖子管理');

-- 评论管理
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (2007, '评论管理', 2000, 7, '/system/comment', '', 'C', '0', '1', 'system:comment:list', 'comment', 'admin', NOW(), '评论管理');

-- 测评管理
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (2008, '测评管理', 2000, 8, '/system/scale', '', 'C', '0', '1', 'system:scale:list', 'chart', 'admin', NOW(), '测评管理');

-- 测评题目
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (2009, '测评题目', 2000, 9, '/system/question', '', 'C', '0', '1', 'system:question:list', 'edit', 'admin', NOW(), '测评题目管理');

-- 测评结果
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (2010, '测评结果', 2000, 10, '/system/result', '', 'C', '0', '1', 'system:result:list', 'form', 'admin', NOW(), '测评结果管理');

-- 报告管理
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (2011, '报告管理', 2000, 11, '/system/report', '', 'C', '0', '1', 'system:report:list', 'file', 'admin', NOW(), '报告管理');

-- 消息记录
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (2012, '消息记录', 2000, 12, '/system/messageRecord', '', 'C', '0', '1', 'system:messageRecord:list', 'message', 'admin', NOW(), '消息记录');

-- 因子管理
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (2013, '因子管理', 2000, 13, '/system/factor', '', 'C', '0', '1', 'system:factor:list', 'list', 'admin', NOW(), '因子管理');

-- 公式管理
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, url, target, menu_type, visible, is_refresh, perms, icon, create_by, create_time, remark)
VALUES (2014, '公式管理', 2000, 14, '/system/formula', '', 'C', '0', '1', 'system:formula:list', 'calculation', 'admin', NOW(), '公式管理');

-- 关联到管理员角色
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(1, 2000), (1, 2001), (1, 2002), (1, 2003), (1, 2004),
(1, 2005), (1, 2006), (1, 2007), (1, 2008), (1, 2009),
(1, 2010), (1, 2011), (1, 2012), (1, 2013), (1, 2014);
