-- 业务表 DDL（从 mapper XML 反推，MySQL 8.0 兼容）
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 专业
DROP TABLE IF EXISTS sys_major;
CREATE TABLE sys_major (
  major_id    bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '专业id',
  major_name  varchar(50)  DEFAULT '' COMMENT '专业名称',
  dept_id     bigint(20)   DEFAULT NULL COMMENT '院系id',
  create_time datetime      DEFAULT NULL COMMENT '创建时间',
  update_time datetime      DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (major_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='专业表';

-- 班级
DROP TABLE IF EXISTS sys_classes;
CREATE TABLE sys_classes (
  classes_id   bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '班级id',
  classes_name varchar(50)  DEFAULT '' COMMENT '班级名称',
  dept_id      bigint(20)   DEFAULT NULL COMMENT '院系id',
  create_time  datetime      DEFAULT NULL COMMENT '创建时间',
  update_time  datetime      DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (classes_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级表';

-- 学生
DROP TABLE IF EXISTS sys_student;
CREATE TABLE sys_student (
  stu_id      bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '学生id',
  login_name  varchar(50)  DEFAULT '' COMMENT '登录名',
  password    varchar(100) DEFAULT '' COMMENT '密码',
  stu_name    varchar(50)  DEFAULT '' COMMENT '学生姓名',
  birth       date          DEFAULT NULL COMMENT '出生日期',
  sex         char(1)      DEFAULT NULL COMMENT '性别',
  grade       varchar(20)  DEFAULT NULL COMMENT '年级',
  major_id    bigint(20)   DEFAULT NULL COMMENT '专业id',
  classes     varchar(50)  DEFAULT NULL COMMENT '班级',
  dept_id     bigint(20)   DEFAULT NULL COMMENT '院系id',
  phone       varchar(11)  DEFAULT NULL COMMENT '手机',
  email       varchar(50)  DEFAULT NULL COMMENT '邮箱',
  avatar      varchar(200) DEFAULT NULL COMMENT '头像',
  status      char(1)      DEFAULT '0' COMMENT '状态（0正常 1停用）',
  create_time datetime      DEFAULT NULL COMMENT '创建时间',
  update_time datetime      DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (stu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生表';

-- 心理知识
DROP TABLE IF EXISTS sys_mental_knowledge;
CREATE TABLE sys_mental_knowledge (
  mental_knowledge_id      bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '知识id',
  mental_knowledge_title   varchar(200) DEFAULT '' COMMENT '标题',
  mental_knowledge_content text COMMENT '内容',
  mental_knowledge_author  varchar(50)  DEFAULT '' COMMENT '作者',
  login_name               varchar(50)  DEFAULT '' COMMENT '发布人登录名',
  mental_knowledge_file    varchar(200) DEFAULT NULL COMMENT '附件',
  status                   char(1)      DEFAULT '0' COMMENT '状态',
  create_time              datetime      DEFAULT NULL COMMENT '创建时间',
  update_time              datetime      DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (mental_knowledge_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='心理知识表';

-- 知识收藏
DROP TABLE IF EXISTS sys_knowledge_collection;
CREATE TABLE sys_knowledge_collection (
  collection_id        bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '收藏id',
  user_id              bigint(20)  DEFAULT NULL COMMENT '用户id',
  mental_knowledge_id  bigint(20)  DEFAULT NULL COMMENT '知识id',
  collection_state     char(1)     DEFAULT '0' COMMENT '收藏状态',
  create_time          datetime     DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (collection_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='知识收藏表';

-- 心理音乐
DROP TABLE IF EXISTS sys_music;
CREATE TABLE sys_music (
  musicid     bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '音乐id',
  title       varchar(100) DEFAULT '' COMMENT '标题',
  singer      varchar(100) DEFAULT '' COMMENT '歌手',
  create_time datetime      DEFAULT NULL COMMENT '创建时间',
  url         varchar(200) DEFAULT '' COMMENT '播放地址',
  PRIMARY KEY (musicid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='心理音乐表';

-- 帖子
DROP TABLE IF EXISTS sys_posts;
CREATE TABLE sys_posts (
  posts_id         bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '帖子id',
  user_id          bigint(20)   DEFAULT NULL COMMENT '用户id',
  posts_title      varchar(200) DEFAULT '' COMMENT '标题',
  posts_content    text COMMENT '内容',
  posts_image      varchar(200) DEFAULT NULL COMMENT '图片',
  comment_num      int(11)      DEFAULT 0 COMMENT '评论数',
  create_time      datetime      DEFAULT NULL COMMENT '创建时间',
  update_time      datetime      DEFAULT NULL COMMENT '更新时间',
  last_com_user_id bigint(20)   DEFAULT NULL COMMENT '最后评论人id',
  last_com_time    datetime      DEFAULT NULL COMMENT '最后评论时间',
  community_type   varchar(50)  DEFAULT NULL COMMENT '社区分类',
  PRIMARY KEY (posts_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='心理社区帖子表';

-- 评论
DROP TABLE IF EXISTS sys_comment;
CREATE TABLE sys_comment (
  comment_id         bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '评论id',
  posts_id           bigint(20)   DEFAULT NULL COMMENT '帖子id',
  user_id            bigint(20)   DEFAULT NULL COMMENT '用户id',
  avatar             varchar(200) DEFAULT NULL COMMENT '头像',
  user_name          varchar(50)  DEFAULT '' COMMENT '用户名',
  parent_user_id     bigint(20)   DEFAULT NULL COMMENT '回复人id',
  parent_comment_id  bigint(20)   DEFAULT NULL COMMENT '父评论id',
  content            text COMMENT '内容',
  create_time        datetime      DEFAULT NULL COMMENT '创建时间',
  update_time        datetime      DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (comment_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论表';

-- 举报
DROP TABLE IF EXISTS sys_report;
CREATE TABLE sys_report (
  report_id       bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '举报id',
  posts_id        bigint(20)   DEFAULT NULL COMMENT '帖子id',
  posts_comment   text COMMENT '被举报内容',
  report_type     varchar(50)  DEFAULT '' COMMENT '举报类型',
  report_comment  varchar(500) DEFAULT '' COMMENT '举报说明',
  report_user_id  bigint(20)   DEFAULT NULL COMMENT '举报人id',
  feedback        varchar(500) DEFAULT NULL COMMENT '反馈',
  create_time     datetime      DEFAULT NULL COMMENT '创建时间',
  update_time     datetime      DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (report_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='举报表';

-- 消息记录
DROP TABLE IF EXISTS sys_message_record;
CREATE TABLE sys_message_record (
  message_id      bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '消息id',
  login_name      varchar(50)  DEFAULT '' COMMENT '发送人',
  to_login_name   varchar(50)  DEFAULT '' COMMENT '接收人',
  message_type    varchar(50)  DEFAULT '' COMMENT '消息类型',
  message_content text COMMENT '消息内容',
  create_time     datetime      DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (message_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息记录表';

-- 量表公式
DROP TABLE IF EXISTS sys_scale_formula;
CREATE TABLE sys_scale_formula (
  formula_id          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '公式id',
  formula_name        varchar(100) DEFAULT '' COMMENT '公式名称',
  formula_constant    decimal(10,2) DEFAULT 0 COMMENT '常数项',
  formula_coefficient varchar(500) DEFAULT '' COMMENT '系数',
  PRIMARY KEY (formula_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='量表公式表';

-- 量表
DROP TABLE IF EXISTS sys_scale;
CREATE TABLE sys_scale (
  scale_id            bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '量表id',
  scale_title         varchar(200) DEFAULT '' COMMENT '量表标题',
  scale_details       text COMMENT '量表说明',
  user_id             bigint(20)   DEFAULT NULL COMMENT '创建人id',
  status              char(1)      DEFAULT '0' COMMENT '状态',
  formula_id          bigint(20)   DEFAULT NULL COMMENT '公式id',
  scale_type          varchar(50)  DEFAULT '' COMMENT '量表类型',
  start_time          datetime      DEFAULT NULL COMMENT '开始时间',
  early_warning_score int(11)      DEFAULT 0 COMMENT '预警分数',
  scale_time          int(11)      DEFAULT 0 COMMENT '答题时长(分钟)',
  end_time            datetime      DEFAULT NULL COMMENT '结束时间',
  create_time         datetime      DEFAULT NULL COMMENT '创建时间',
  update_tiem         datetime      DEFAULT NULL COMMENT '更新时间(原文拼写)',
  PRIMARY KEY (scale_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='心理量表表';

-- 量表因子
DROP TABLE IF EXISTS sys_scale_factor;
CREATE TABLE sys_scale_factor (
  factor_id     bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '因子id',
  factor_name   varchar(100) DEFAULT '' COMMENT '因子名称',
  scale_id      bigint(20)   DEFAULT NULL COMMENT '量表id',
  factor_result varchar(500) DEFAULT '' COMMENT '因子结果',
  formula_id    bigint(20)   DEFAULT NULL COMMENT '公式id',
  create_time   datetime      DEFAULT NULL COMMENT '创建时间',
  update_time   datetime      DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (factor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='量表因子表';

-- 量表题目
DROP TABLE IF EXISTS sys_scale_question;
CREATE TABLE sys_scale_question (
  question_id     bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '题目id',
  scale_id        bigint(20)   DEFAULT NULL COMMENT '量表id',
  scale_title     varchar(200) DEFAULT '' COMMENT '量表标题',
  question_order  int(11)      DEFAULT 0 COMMENT '题目顺序',
  question_content text COMMENT '题目内容',
  factor_id       bigint(20)   DEFAULT NULL COMMENT '因子id',
  create_time     datetime      DEFAULT NULL COMMENT '创建时间',
  update_time     datetime      DEFAULT NULL COMMENT '更新时间',
  factor_name     varchar(100) DEFAULT '' COMMENT '因子名称',
  PRIMARY KEY (question_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='量表题目表';

-- 量表题目选项（含评分）
DROP TABLE IF EXISTS sys_scale_question_answer;
CREATE TABLE sys_scale_question_answer (
  question_id     bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '题目id',
  scale_id        bigint(20)   DEFAULT NULL COMMENT '量表id',
  question_content text COMMENT '题目内容',
  question_order  int(11)      DEFAULT 0 COMMENT '题目顺序',
  question_a      varchar(200) DEFAULT '' COMMENT '选项A',
  question_b      varchar(200) DEFAULT '' COMMENT '选项B',
  question_c      varchar(200) DEFAULT '' COMMENT '选项C',
  question_d      varchar(200) DEFAULT '' COMMENT '选项D',
  question_e      varchar(200) DEFAULT '' COMMENT '选项E',
  score_a         int(11)      DEFAULT 0 COMMENT 'A得分',
  score_b         int(11)      DEFAULT 0 COMMENT 'B得分',
  score_c         int(11)      DEFAULT 0 COMMENT 'C得分',
  score_d         int(11)      DEFAULT 0 COMMENT 'D得分',
  score_e         int(11)      DEFAULT 0 COMMENT 'E得分',
  factor_id       bigint(20)   DEFAULT NULL COMMENT '因子id',
  create_time     datetime      DEFAULT NULL COMMENT '创建时间',
  update_time     datetime      DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (question_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='量表题目选项表';

-- 量表答案
DROP TABLE IF EXISTS sys_scale_answer;
CREATE TABLE sys_scale_answer (
  answer_id       bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '答案id',
  question_id     bigint(20)   DEFAULT NULL COMMENT '题目id',
  question_content text COMMENT '题目内容',
  scale_id        bigint(20)   DEFAULT NULL COMMENT '量表id',
  answer_option   varchar(200) DEFAULT '' COMMENT '答案选项',
  score           int(11)      DEFAULT 0 COMMENT '得分',
  create_time     datetime      DEFAULT NULL COMMENT '创建时间',
  update_time     datetime      DEFAULT NULL COMMENT '更新时间',
  factor_id       bigint(20)   DEFAULT NULL COMMENT '因子id',
  PRIMARY KEY (answer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='量表答案表';

-- 量表结果定义
DROP TABLE IF EXISTS sys_scale_result;
CREATE TABLE sys_scale_result (
  result_id       bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '结果id',
  scale_id        bigint(20)   DEFAULT NULL COMMENT '量表id',
  scale_title     varchar(200) DEFAULT '' COMMENT '量表标题',
  result_details  text COMMENT '结果说明',
  result_content  text COMMENT '结果内容',
  create_time     datetime      DEFAULT NULL COMMENT '创建时间',
  update_time     datetime      DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (result_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='量表结果定义表';

-- 用户测评结果
DROP TABLE IF EXISTS sys_scale_user_result;
CREATE TABLE sys_scale_user_result (
  result_id      bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '结果id',
  scale_id       bigint(20)   DEFAULT NULL COMMENT '量表id',
  scale_title    varchar(200) DEFAULT '' COMMENT '量表标题',
  user_id        bigint(20)   DEFAULT NULL COMMENT '用户id',
  login_name     varchar(50)  DEFAULT '' COMMENT '登录名',
  user_name      varchar(50)  DEFAULT '' COMMENT '用户名',
  count_score    int(11)      DEFAULT 0 COMMENT '总得分',
  classes_name   varchar(50)  DEFAULT '' COMMENT '班级',
  factor_score   varchar(500) DEFAULT '' COMMENT '因子得分',
  dept_name      varchar(50)  DEFAULT '' COMMENT '院系',
  result_content text COMMENT '结果内容',
  factor_result  text COMMENT '因子结果',
  user_answer    text COMMENT '用户答案',
  create_time    datetime      DEFAULT NULL COMMENT '创建时间',
  update_time    datetime      DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (result_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户测评结果表';

-- sys_user 表补充作者扩展字段（RuoYi 基础表无这两列）
ALTER TABLE sys_user ADD COLUMN major_id bigint(20) DEFAULT NULL COMMENT '专业id' AFTER dept_id;
ALTER TABLE sys_user ADD COLUMN classes_id bigint(20) DEFAULT NULL COMMENT '班级id' AFTER major_id;

SET FOREIGN_KEY_CHECKS = 1;
