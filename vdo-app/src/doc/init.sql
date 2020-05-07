CREATE TABLE `ed_map` (
  `id` bigint(20) NOT NULL,
  `x` int(4) DEFAULT NULL,
  `y` int(4) DEFAULT NULL,
  `level` varchar(10) DEFAULT NULL COMMENT '地图级别',
  `source` varchar(255) DEFAULT NULL,
  `cname` varchar(200) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `userId` varchar(64) DEFAULT NULL,
  `boxId` varchar(64) DEFAULT NULL,
  `examId` varchar(64) DEFAULT NULL,
  `examType` varchar(20) DEFAULT NULL,
  `free` int(4) DEFAULT NULL,
  `price` double(10,2) DEFAULT NULL,
  `score` int(4) DEFAULT NULL,
  `needScore` int(4) DEFAULT NULL,
  `inLevelJson` varchar(255) DEFAULT NULL,
  `nextLevelJson` varchar(255) DEFAULT NULL,
  `orders` bigint(20) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_exam_id` (`examId`) USING BTREE,
  KEY `idx_box_id` (`boxId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- update ed_map set examId = 'd95ef906-d1d5-46cb-89d2-ab52c40c037d' where mapID like '%1';
-- update ed_map set examId = '201c4ac0-d838-45de-a5a6-9d30c98c1fa6' where mapID like '%2';
-- update ed_map set examId = '05669c00-a5fb-4ee2-b12e-dcb76025a343' where mapID like '%3';
-- update ed_map set examId = 'd95ef906-d1d5-46cb-89d2-ab52c40c037d' where mapID like '%4';
-- update ed_map set examId = '201c4ac0-d838-45de-a5a6-9d30c98c1fa6' where mapID like '%5';
-- update ed_map set examId = '05669c00-a5fb-4ee2-b12e-dcb76025a343' where mapID like '%6';

ALTER TABLE `ed_exam_info`
    ADD `setter` VARCHAR(32) COMMENT '作者' AFTER `id`;
ALTER TABLE `ed_exam_info`
    ADD `level` VARCHAR(8) COMMENT '等级' AFTER `title`;
ALTER TABLE `ed_exam_info`
    ADD `exam_type` VARCHAR(300) COMMENT '试卷类型' AFTER `level`;
ALTER TABLE `ed_exam_info`
    ADD `video_id` BIGINT(20) COMMENT '视频id' AFTER `exam_type`;
ALTER TABLE `ed_exam_info`
    ADD `question_num` INT(11) COMMENT '试题数' AFTER `video_id`;
ALTER TABLE `ed_exam_info`
    ADD `comprehension_num` INT(11) COMMENT '阅读理解题数' AFTER `question_num`;
ALTER TABLE `ed_exam_info`
    ADD `vocabulary_num` INT(11) COMMENT '词汇题数' AFTER `comprehension_num`;
ALTER TABLE `ed_exam_info`
    ADD `grammar_num` INT(11) COMMENT 'vocabularyNum' AFTER `vocabulary_num`;
ALTER TABLE `ed_exam_info`
    ADD `partListJson` TEXT CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'vocabularyNum' AFTER `grammar_num`;

ALTER TABLE `ed_exam_user_info`
    ADD `retry_time` INT(11) COMMENT '重试提交次数' AFTER `user_id`;
ALTER TABLE `ed_exam_user_info`
    ADD `answer_id` BIGINT(20) COMMENT '答案 id' AFTER `user_id`;

CREATE TABLE `ed_answer` (
    `id`          BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `examId`      VARCHAR(200) NOT NULL,
    `userId`      VARCHAR(200) NOT NULL,
    `answersJson` TEXT CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;


CREATE TABLE `ed_part` (
    `part_id`        BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `exam_info_id`   VARCHAR(200) NOT NULL,
    `part_name`      TEXT CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `orders`         INT(11)      NOT NULL,
    `status`         TINYINT(4)                                      DEFAULT NULL,
    `create_time`    TIMESTAMP                                       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    TIMESTAMP                                       DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_account` INT(11)                                         DEFAULT NULL COMMENT '创建人',
    `update_account` INT(11)                                         DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`part_id`),
    KEY `idx_exam_info_id`(`exam_info_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

CREATE TABLE `ed_node` (
    `node_id`                    BIGINT(20) NOT NULL AUTO_INCREMENT,
    `part_id`                    BIGINT(20) NOT NULL,
    `question_id`                BIGINT(20)                                              DEFAULT NULL,
    `text`                       TEXT CHARACTER SET utf8 COLLATE utf8_general_ci         DEFAULT NULL,
    `type`                       VARCHAR(10)                                             DEFAULT NULL,
    `choose_type`                VARCHAR(5)                                              DEFAULT NULL,
    `title_text`                 TEXT CHARACTER SET utf8 COLLATE utf8_general_ci         DEFAULT NULL,
    `title_img`                  VARCHAR(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `option_list_json`           TEXT CHARACTER SET utf8 COLLATE utf8_general_ci         DEFAULT NULL,
    `img_fill_content_list_json` TEXT CHARACTER SET utf8 COLLATE utf8_general_ci         DEFAULT NULL,
    `number`                     BIGINT(20)                                              DEFAULT NULL,
    `left_json`                  TEXT CHARACTER SET utf8 COLLATE utf8_general_ci         DEFAULT NULL,
    `right_json`                 TEXT CHARACTER SET utf8 COLLATE utf8_general_ci         DEFAULT NULL,
    `answer`                     VARCHAR(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `orders`                     INT(11)    NOT NULL,
    `status`                     TINYINT(4)                                              DEFAULT NULL,
    `create_time`                TIMESTAMP                                               DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`                TIMESTAMP                                               DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_account`             INT(11)                                                 DEFAULT NULL COMMENT '创建人',
    `update_account`             INT(11)                                                 DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`node_id`),
    KEY `idx_part_id`(`part_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

ALTER TABLE `ed_video`
    MODIFY COLUMN `ID` BIGINT(20);
ALTER TABLE `ed_video`
    MODIFY COLUMN `type` VARCHAR(300);
ALTER TABLE `ed_video`
    MODIFY COLUMN `level` VARCHAR(300);

UPDATE ed_video
SET imgUrl = replace(imgUrl, 'vdocms', 'app/static/image');
UPDATE ed_video
SET video_thumb = replace(video_thumb, 'vdocms', 'app/static/image');

CREATE TABLE `ed_subtitle` (
    `id`           BIGINT(20) NOT NULL AUTO_INCREMENT,
    `video_id`     BIGINT(20)    DEFAULT NULL,
    `title`        VARCHAR(500)  DEFAULT NULL,
    `beginTime`    VARCHAR(50)   DEFAULT NULL,
    `endTime`      VARCHAR(50)   DEFAULT NULL,
    `english`      VARCHAR(1000) DEFAULT NULL,
    `simp_chinese` VARCHAR(1000) DEFAULT NULL,
    `trad_chinese` VARCHAR(1000) DEFAULT NULL,
    `status`       VARCHAR(10)   DEFAULT NULL,
    `timeSs`       VARCHAR(255)  DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_video_id`(`video_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

CREATE TABLE `ed_column` (
    `ColumnID` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `Index`    INT(11)     DEFAULT NULL,
    `ShowFont` VARCHAR(50) DEFAULT NULL,
    `status`   TINYINT(4)  DEFAULT NULL,
    PRIMARY KEY (`ColumnID`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

-- 21世纪杯活动配置表 begin
-- auto-generated definition
CREATE TABLE yj_user_bm_info (
    id INT AUTO_INCREMENT
        PRIMARY KEY,
    teacher_call INT DEFAULT 0 NULL COMMENT '老师称呼',
    teacher_name VARCHAR(100) NULL COMMENT '老师姓名',
    teacher_email VARCHAR(100) NULL COMMENT '老师邮箱',
    teacher_tel VARCHAR(100) NULL COMMENT '老师邮箱',
    school_area VARCHAR(100) NULL COMMENT '学校区域',
    school_id INT NULL COMMENT '学校',
    name_ch VARCHAR(100) NOT NULL COMMENT '姓名中文',
    name_en VARCHAR(100) NULL COMMENT '姓名英文',
    sex INT DEFAULT 0 NOT NULL COMMENT '状态：0.女 1.男',
    grade INT DEFAULT 0 NULL COMMENT '年级',
    group_type INT NULL COMMENT '分组:1.初小 2.高小 3.初中 4.高中',
    create_time VARCHAR(50) NULL COMMENT '创建时间',
    email VARCHAR(100) NULL COMMENT '电子邮箱',
    tel VARCHAR(50) NULL COMMENT '电话',
    content TEXT NULL COMMENT '演讲稿',
    video_url VARCHAR(100) NULL COMMENT '视频链接',
    type INT DEFAULT 0 NULL COMMENT '0.个人报名 1.老师报名',
    upload_status INT DEFAULT 0 NULL COMMENT '上传状态：0.未上传 1.已上传 2.未上传视频 3.未上传演讲稿',
    score_status INT DEFAULT 0 NULL COMMENT '评分状态：0.未评分 1.已评分',
    score VARCHAR(10) NULL,
    score_count INT DEFAULT 0 NULL COMMENT '评分次数',
    event_id VARCHAR(10) NULL,
    user_id VARCHAR(10) NULL COMMENT '用户ID',
    register_code VARCHAR(20) NULL COMMENT '注册码',
    lock_status INT(1) DEFAULT 0 NOT NULL COMMENT '评分加锁状态：0.正常 1.正在评分',
    school_other_name VARCHAR(100) NULL COMMENT '填写其他学校名称'
)
    COMMENT '用户报名信息表'
    CHARSET = utf8;



-- auto-generated definition
CREATE TABLE yj_score_record (
    id          INT(10) AUTO_INCREMENT COMMENT 'ID'
        PRIMARY KEY,
    user_id     VARCHAR(10) NULL COMMENT '评分用户ID',
    bm_id       VARCHAR(10) NULL COMMENT '报名信息ID',
    score       DOUBLE      NULL COMMENT '评分数',
    create_time VARCHAR(20) NULL COMMENT '评分日期'
)
    COMMENT '评分记录表'
    CHARSET = utf8;

-- auto-generated definition
CREATE TABLE yj_school (
    id          INT(10) AUTO_INCREMENT COMMENT 'ID'
        PRIMARY KEY,
    distinct_en VARCHAR(100) NULL COMMENT '地区',
    name_ch     VARCHAR(100) NULL COMMENT '学校名称中文',
    name_en     VARCHAR(100) NULL COMMENT '学校名称英文',
    `distinct`  VARCHAR(100) NULL COMMENT '英文地区'
)
    COMMENT '演讲学校表'
    CHARSET = utf8;

-- auto-generated definition
CREATE TABLE yj_event_news (
    id          INT AUTO_INCREMENT COMMENT 'ID'
        PRIMARY KEY,
    title_ch    VARCHAR(200)  NOT NULL COMMENT '标题',
    title_hk    VARCHAR(200)  NULL,
    title_en    VARCHAR(200)  NULL,
    content_hk  VARCHAR(2000) NULL,
    content_ch  VARCHAR(2000) NULL COMMENT '内容',
    content_en  VARCHAR(2000) NULL,
    event_id    VARCHAR(100)  NOT NULL COMMENT '活动ID',
    create_user VARCHAR(50)   NULL COMMENT '创建人',
    create_time VARCHAR(50)   NULL COMMENT '创建时间',
    update_user VARCHAR(50)   NULL COMMENT '修改人',
    update_time VARCHAR(50)   NULL COMMENT '修改时间',
    img_url     VARCHAR(200)  NULL
)
    COMMENT '活动新闻'
    CHARSET = utf8;

-- auto-generated definition
CREATE TABLE yj_event_info (
    id INT AUTO_INCREMENT COMMENT 'ID'
        PRIMARY KEY,
    name_ch VARCHAR(500) NULL COMMENT '中文活动名称',
    name_hk VARCHAR(500) NULL COMMENT '繁体活动名称',
    name_en VARCHAR(500) NULL COMMENT '英文活动名称',
    banner_url VARCHAR(200) NULL COMMENT 'banner图地址',
    banner_square_url VARCHAR(200) NULL COMMENT '正方图地址',
    sign_up_json VARCHAR(2000) NULL COMMENT '参赛须知',
    pdf_json VARCHAR(2000) NULL COMMENT 'PDF文件信息',
    create_user VARCHAR(50) NULL COMMENT '创建人',
    create_time VARCHAR(50) NULL COMMENT '创建时间',
    update_user VARCHAR(50) NULL COMMENT '修改人',
    update_time VARCHAR(50) NULL COMMENT '修改时间',
    description_ch VARCHAR(2000) NOT NULL COMMENT '中文简介',
    description_hk VARCHAR(2000) NOT NULL COMMENT '繁体简介',
    description_en VARCHAR(2000) NOT NULL COMMENT '英文简介',
    pic_json TEXT NULL COMMENT '历届比赛图片',
    video_json TEXT NULL COMMENT '视频配置',
    sponsors_json TEXT NULL COMMENT '赞助商配置',
    guests_json TEXT NULL COMMENT '嘉宾配置',
    players_json TEXT NULL COMMENT '选手感言配置',
    jury_organ_json TEXT NULL COMMENT '评委结构配置',
    status INT DEFAULT 0 NULL COMMENT '状态：0.默认 1.上架 2.下架',
    start_time VARCHAR(20) NULL COMMENT '开始时间',
    end_time VARCHAR(20) NULL COMMENT '结束时间'
)
    COMMENT '活动信息'
    CHARSET = utf8;



-- auto-generated definition
CREATE TABLE ed_word_pk (
    id          INT AUTO_INCREMENT
        PRIMARY KEY,
    title       VARCHAR(200) NULL COMMENT '标题',
    explain_ch  VARCHAR(200) NULL COMMENT '解释简体中文',
    explain_hk  VARCHAR(200) NULL COMMENT '解释繁体中文',
    create_time VARCHAR(20)  NULL COMMENT '创建时间',
    update_time VARCHAR(20)  NULL COMMENT '更新时间',
    create_user VARCHAR(20)  NULL COMMENT '创建人',
    update_user VARCHAR(20)  NULL COMMENT '修改人',
    level       VARCHAR(10)  NULL COMMENT '级别'
)
    COMMENT '单词PK'
    CHARSET = utf8;

-- auto-generated definition
CREATE TABLE ed_feedback (
    id          INT AUTO_INCREMENT
        PRIMARY KEY,
    file_json   VARCHAR(2000) NULL COMMENT '附件json',
    contact     VARCHAR(200)  NULL COMMENT '联系方式',
    content     TEXT          NULL COMMENT '反馈内容',
    create_time VARCHAR(20)   NULL COMMENT '提交时间',
    status      INT           NULL COMMENT '处理状态：0.待处理 1.已处理 2.处理中'
)
    COMMENT '问题反馈'
    CHARSET = utf8;

-- auto-generated definition
CREATE TABLE ed_goods (
    id             INT AUTO_INCREMENT COMMENT '商品ID'
        PRIMARY KEY,
    prop_id        INT           NULL COMMENT '道具ID',
    sort_no        VARCHAR(10)   NULL COMMENT '序号',
    goods_badge    VARCHAR(10)   NULL COMMENT '商品角标',
    discount       DOUBLE        NULL COMMENT '折扣',
    price          DOUBLE        NULL COMMENT '商品原价',
    discount_price DOUBLE        NULL COMMENT '折后价',
    status         INT DEFAULT 0 NULL COMMENT '状态：0.默认 1.上架 2.下架',
    create_time    VARCHAR(20)   NULL COMMENT '创建时间',
    create_user    VARCHAR(20)   NULL COMMENT '创建人',
    update_user    VARCHAR(20)   NULL COMMENT '更新人',
    update_time    VARCHAR(20)   NULL COMMENT '更新时间'
)
    COMMENT '商品'
    CHARSET = utf8;

-- auto-generated definition
CREATE TABLE ed_launch_image (
    id          INT AUTO_INCREMENT COMMENT '主键'
        PRIMARY KEY,
    title       VARCHAR(2000) NULL COMMENT '标题',
    link        VARCHAR(100)  NULL COMMENT '链接',
    iphone      VARCHAR(200)  NULL COMMENT 'iphone启动图',
    ipad        VARCHAR(200)  NULL COMMENT 'IPad启动图',
    android     VARCHAR(200)  NULL COMMENT '安卓启动图',
    android_pad VARCHAR(200)  NULL COMMENT '安卓pad',
    start_time  VARCHAR(20)   NULL COMMENT '开始时间',
    end_time    VARCHAR(20)   NULL COMMENT '结束时间',
    create_user VARCHAR(20)   NULL COMMENT '创建人',
    create_time VARCHAR(20)   NULL COMMENT '创建时间',
    update_user VARCHAR(20)   NULL COMMENT '更新人',
    update_time VARCHAR(20)   NULL COMMENT '更新时间'
)
    COMMENT '启动图'
    CHARSET = utf8;

-- auto-generated definition
CREATE TABLE ed_partner_class (
    id              INT AUTO_INCREMENT COMMENT 'ID'
        PRIMARY KEY,
    name            VARCHAR(200) NULL COMMENT '班级名称',
    teacher_code_id INT          NULL COMMENT '老师codeId',
    level           VARCHAR(20)  NULL COMMENT '级别：S1、S2、S3',
    school_id       INT          NULL COMMENT '学校ID',
    num_count       INT          NULL COMMENT '生成code数量',
    create_time     VARCHAR(20)  NULL COMMENT '创建日期',
    create_user     VARCHAR(20)  NULL COMMENT '创建人',
    update_time     VARCHAR(20)  NULL COMMENT '更新日期',
    update_user     VARCHAR(20)  NULL COMMENT '更新人'
)
    COMMENT '合作方班级'
    CHARSET = utf8;

-- auto-generated definition
CREATE TABLE ed_partner_code (
    id            INT AUTO_INCREMENT COMMENT '主键ID'
        PRIMARY KEY,
    identify_code VARCHAR(50)   NULL COMMENT '验证码',
    status        INT DEFAULT 0 NOT NULL COMMENT '状态：0.未使用 1.已使用',
    user_id       VARCHAR(20)   NULL COMMENT '用户ID',
    school_id     INT           NULL COMMENT '合作学校ID',
    type          INT DEFAULT 0 NULL COMMENT '类型：0.学生 1.老师（老师码在合作方页面生成查看）',
    activate_time VARCHAR(20)   NULL COMMENT '激活日期',
    class_id      INT           NULL COMMENT '班级ID'
)
    COMMENT '合作方code码'
    CHARSET = utf8;

-- auto-generated definition
CREATE TABLE ed_partner_school (
    id          INT AUTO_INCREMENT COMMENT '主键ID'
        PRIMARY KEY,
    name        VARCHAR(200) NULL COMMENT '合作方名称',
    contact     VARCHAR(200) NULL COMMENT '联系方式',
    num_count   INT          NULL COMMENT '生成code数量',
    start_time  VARCHAR(20)  NULL COMMENT '开始时间',
    end_time    VARCHAR(20)  NULL COMMENT '结束时间',
    create_time VARCHAR(20)  NULL COMMENT '创建时间',
    create_user VARCHAR(20)  NULL COMMENT '创建人',
    update_time VARCHAR(20)  NULL COMMENT '更新时间',
    update_user VARCHAR(20)  NULL COMMENT '更新人'
)
    COMMENT '合作方学校'
    CHARSET = utf8;

-- auto-generated definition
CREATE TABLE ed_prop (
    id          INT AUTO_INCREMENT COMMENT 'ID'
        PRIMARY KEY,
    image_url   VARCHAR(200)  NULL COMMENT '道具图片',
    prop_name   VARCHAR(200)  NULL COMMENT '道具名称',
    price       VARCHAR(200)  NULL COMMENT '道具价格',
    bamboo      VARCHAR(200)  NULL COMMENT '获得竹子',
    exp         VARCHAR(200)  NULL COMMENT '经验',
    description VARCHAR(2000) NULL COMMENT '道具描述',
    create_time VARCHAR(20)   NULL COMMENT '创建时间',
    create_user VARCHAR(20)   NULL COMMENT '创建人',
    update_time VARCHAR(20)   NULL COMMENT '修改时间',
    update_user VARCHAR(20)   NULL COMMENT '更新人'
)
    COMMENT '道具'
    CHARSET = utf8;


-- auto-generated definition
CREATE TABLE ed_box (
    id          INT AUTO_INCREMENT COMMENT '宝箱ID'
        PRIMARY KEY,
    box_type    VARCHAR(200)  NULL COMMENT '宝箱类型',
    bamboo      VARCHAR(10)   NULL COMMENT '竹子数量',
    icon_img    VARCHAR(200)  NULL COMMENT '图标',
    box_img     VARCHAR(200)  NULL COMMENT '宝箱详图',
    drop_json   VARCHAR(2000) NULL COMMENT '掉落物品json',
    create_time VARCHAR(20)   NULL COMMENT '创建时间',
    create_user VARCHAR(20)   NULL COMMENT '创建人',
    update_time VARCHAR(20)   NULL COMMENT '更新时间',
    update_user VARCHAR(20)   NULL COMMENT '更新人'
)
    COMMENT '宝箱'
    CHARSET = utf8;


-- auto-generated definition
CREATE TABLE ed_cms_user (
    id          INT AUTO_INCREMENT
        PRIMARY KEY,
    name        VARCHAR(100)            NOT NULL COMMENT '姓名',
    exec_count  INT                     NULL COMMENT '可评分次数',
    account     VARCHAR(100)            NOT NULL COMMENT '账号',
    password    VARCHAR(100)            NULL COMMENT '密码',
    status      INT         DEFAULT 0   NOT NULL COMMENT '状态：0.未开通 1.已开通',
    group_types VARCHAR(20) DEFAULT '0' NULL COMMENT '评分组别权限：0.未开通 1.',
    create_user VARCHAR(50)             NULL COMMENT '创建人',
    create_time VARCHAR(50)             NULL COMMENT '创建时间',
    update_user VARCHAR(50)             NULL COMMENT '修改人',
    update_time VARCHAR(50)             NULL COMMENT '修改时间',
    role_type   INT(2)                  NULL COMMENT '角色：1.超管 2.运营 3.老师 4.评委'
)
    COMMENT '后台用户表'
    CHARSET = utf8;

-- auto-generated definition
CREATE TABLE ed_banner_image (
    id          INT AUTO_INCREMENT COMMENT '主键'
        PRIMARY KEY,
    title       VARCHAR(2000) NULL COMMENT '标题',
    link        VARCHAR(100)  NULL COMMENT '链接',
    pc          VARCHAR(200)  NULL COMMENT 'pc banner',
    iphone      VARCHAR(200)  NULL COMMENT 'iphone banner',
    ipad        VARCHAR(200)  NULL COMMENT 'IPad banner',
    android     VARCHAR(200)  NULL COMMENT '安卓 banner',
    start_time  VARCHAR(20)   NULL COMMENT '开始时间',
    end_time    VARCHAR(20)   NULL COMMENT '结束时间',
    create_user VARCHAR(20)   NULL COMMENT '创建人',
    create_time VARCHAR(20)   NULL COMMENT '创建时间',
    update_user VARCHAR(20)   NULL COMMENT '更新人',
    update_time VARCHAR(20)   NULL COMMENT '更新时间'
)
    COMMENT 'banner'
    CHARSET = utf8;

-- auto-generated definition
CREATE TABLE ed_email (
    id              INT AUTO_INCREMENT COMMENT '主键ID'
        PRIMARY KEY,
    subject         VARCHAR(2000) NULL COMMENT '邮件主题',
    content         TEXT          NULL COMMENT '邮件内容',
    create_time     VARCHAR(20)   NULL COMMENT '创建时间',
    update_time     VARCHAR(20)   NULL COMMENT '更新时间',
    create_user     VARCHAR(20)   NULL COMMENT '创建人',
    update_user     VARCHAR(20)   NULL COMMENT '修改人',
    addressees      VARCHAR(2000) NULL COMMENT '收件人',
    status          INT DEFAULT 0 NULL COMMENT '状态：0.未发送 1.已发送 2.等待发送',
    send_time       VARCHAR(20)   NULL COMMENT '发送时间',
    attachment_json VARCHAR(3000) NULL COMMENT '附件json'
)
    COMMENT '邮件信息'
    CHARSET = utf8;










-- 后台用户初始化超级管理员账号
INSERT INTO vdo1.ed_cms_user (name, exec_count, account, password, status, group_types, create_user, create_time,
                              update_user, update_time, role_type)
VALUES ('admin', NULL, 'admin', 'admin', 1, '0', '', '2019-08-22 16:55:01', NULL, NULL, 1);
-- 21世纪杯活动配置表 end


-- 收藏视频和点赞视频合为一处，新加type区分
ALTER TABLE ed_collect ADD COLUMN type VARCHAR(20) DEFAULT 'collect' COMMENT '类型收藏collect点赞praise';


CREATE TABLE ed_new_word (
    id          INT AUTO_INCREMENT COMMENT '主键' PRIMARY KEY,
    userId      varchar(200) NULL COMMENT '用户 id',
    word        VARCHAR(200)  NULL COMMENT '生词',
    state           tinyint(4)  NULL COMMENT '状态',
  `addDate` date DEFAULT NULL COMMENT '登记时间'
)
    COMMENT '生词本'
    CHARSET = utf8;

CREATE TABLE ed_wrong_topic (
    id          INT AUTO_INCREMENT COMMENT '主键' PRIMARY KEY,
    userId      varchar(200) NULL COMMENT '用户 id',
    examId      varchar(200) NULL COMMENT '试卷 id',
    nodeId      varchar(200) NULL COMMENT '错题 id',
    node        text  NULL COMMENT '错题',
    answer        text  NULL COMMENT '错题答案',
    state           tinyint(4)  NULL COMMENT '状态',
  `addDate` datetime DEFAULT NULL COMMENT '登记时间'
)
    COMMENT '错题本'
    CHARSET = utf8;

-- game 模块 begin
CREATE TABLE ed_word_pk_record (
    id INT AUTO_INCREMENT COMMENT '主键',
    user_id INT NULL COMMENT '用户ID',
    is_victory INT DEFAULT 0 NULL COMMENT '胜负：1.胜利 0.失败',
    bamboo_reward INT NULL COMMENT '竹子奖励',
    create_time VARCHAR(20) NULL COMMENT 'PK时间',
    CONSTRAINT ed_word_pk_record_id_pk
        PRIMARY KEY (id)
)
    COMMENT '用户单词pk记录'
    CHARSET utf8;

CREATE TABLE ed_pet (
    id INT AUTO_INCREMENT COMMENT '主键'
        PRIMARY KEY,
    user_id INT NULL COMMENT '用户ID',
    growth INT NULL COMMENT '成长值',
    create_time VARCHAR(20) NULL COMMENT '出生日期'
)
    COMMENT '宠物属性表'
    CHARSET = utf8;


CREATE TABLE ed_pet_prop (
    id INT AUTO_INCREMENT COMMENT '主键'
        PRIMARY KEY,
    pet_id INT NULL COMMENT '宠物ID',
    prop_id INT NULL COMMENT '道具ID',
    amount INT NULL COMMENT '数量'
)
    COMMENT '宠物道具'
    CHARSET = utf8;

CREATE TABLE ed_pet_order_record (
    id INT AUTO_INCREMENT COMMENT '主键'
        PRIMARY KEY,
    goods_id INT NULL COMMENT '商品ID',
    pet_id INT NULL COMMENT '宠物ID',
    amount INT NULL COMMENT '数量',
    total_value DOUBLE NULL COMMENT '总价',
    create_time VARCHAR(20) NULL COMMENT '购买日期'
)
    COMMENT '道具购买记录'
    CHARSET = utf8;

CREATE TABLE ed_pet_log (
    id INT AUTO_INCREMENT COMMENT '主键'
        PRIMARY KEY,
    action_type INT DEFAULT 1 NOT NULL COMMENT '活动类别：1.默认喂食 *其他后期考虑',
    pet_id INT NULL COMMENT '宠物ID',
    create_time VARCHAR(20) NULL COMMENT '操作时间'
)
    COMMENT '宠物喂食活动记录'
    CHARSET = utf8;

CREATE TABLE ed_work_pk_robot (
    id         INT AUTO_INCREMENT COMMENT '主键ID'
        PRIMARY KEY,
    robot_name VARCHAR(20)  NULL COMMENT '机器人名称',
    head_url   VARCHAR(100) NULL COMMENT '机器人头像'
)
    COMMENT '单词pk机器人信息'
    CHARSET utf8;
-- game 模块 end


ALTER TABLE ed_user ADD COLUMN sex VARCHAR(6) DEFAULT 'male' COMMENT '男性=male，女性=female';
ALTER TABLE ed_user ADD COLUMN language INT(2) DEFAULT 2 COMMENT '语言偏好，默认繁体中文。简体中文=1，繁体中文=2，英文=3';
ALTER TABLE ed_user ADD COLUMN introduction TEXT COMMENT '个人简介、签名';
ALTER TABLE ed_user ADD COLUMN purpose INT(2) DEFAULT 3 COMMENT '学习目的，默认提高DSE。考TOFEL=1，考IELTS=2，提高DSE=3，可扩充';
ALTER TABLE ed_user ADD COLUMN birthday VARCHAR(10) DEFAULT '2018-01-01' COMMENT '生日，2019-02-12';

CREATE TABLE `ed_download` (
    `id`  int(11) NOT NULL AUTO_INCREMENT ,
    `user_id`  int(11) NULL DEFAULT NULL COMMENT '用户ID',
    `video_id`  int(11) NULL DEFAULT NULL COMMENT '视频ID',
    `createTime`  varchar(20) NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
)
    COMMENT '用户下载记录表'
    CHARSET utf8;


ALTER TABLE `ed_user`
-- DROP COLUMN `facebookUID`,
ADD COLUMN `s`  varchar(5) NULL DEFAULT 'S1' COMMENT '年级，S1-S6' AFTER `birthday`,
ADD COLUMN `level`  int(10) NULL DEFAULT 1 COMMENT '用户等级，靠时间、做题、养宠物等获得经验升级' AFTER `s`,
ADD COLUMN `vipLevel`  int(50) NULL DEFAULT -1 COMMENT 'vip等级，考试充值获得经验升级' AFTER `level`,
ADD COLUMN `avatar`  int(20) NULL DEFAULT 1 COMMENT '熊猫酱的avatar换装衣服id' AFTER `vipLevel`,
ADD COLUMN `email`  varchar(50) NULL AFTER `avatar`;

CREATE TABLE `ed_user_third_login` (
`id`  int(20) NOT NULL COMMENT '用户唯一id' ,
`facebookUID`  int(50) NULL DEFAULT NULL COMMENT '第三方登录是脸书时的UID' ,
`twitterUID`  int(50) NULL DEFAULT NULL COMMENT '第三方登录是推特时的UID' ,
`weChatUID`  int(50) NULL DEFAULT NULL COMMENT '第三方登录是微信时的UID' ,
`QQUID`  int(50) NULL DEFAULT NULL COMMENT '第三方登录是QQ时的UID' ,
`weiboUID`  int(50) NULL DEFAULT NULL COMMENT '第三方登录是微博时的UID' ,
PRIMARY KEY (`id`)
)
    COMMENT '第三方登录认证表'
    CHARSET utf8;

CREATE TABLE `ed_user_study` (
`id`  int(20) NOT NULL COMMENT '用户唯一id' ,
`studyTimeToday`  int(20) NULL DEFAULT 0 COMMENT '今日学习时间，精确到分钟，每日数据凌晨4点重置' ,
`studyTimeWeek`  int(20) NULL DEFAULT 0 COMMENT '本周学习时间，精确到分钟，每周数据周一凌晨4点重置' ,
`studyTimeAll`  int(20) NULL DEFAULT 0 COMMENT '总学习时间，精确到分钟' ,
`studyDayAll`  int(20) NULL DEFAULT 0 COMMENT '总学习天数，精确到天，即每天学习1次就算做这天学习了' ,
`studyDayContinue`  int(20) NULL DEFAULT 0 COMMENT '连续学习天数，精确到天，遇到没学习的，重置从0开始。' ,
`studyTimeWeekMax`  int(20) NULL DEFAULT 0 COMMENT '本周学习时长最久，精确到分钟' ,
`studyTimeWeekMaxDate`  varchar(10) NULL DEFAULT '2018-01-01' COMMENT '达成本周学习时长最久的日期，2019-02-12' ,
`finishVDOWeek`  int(10) NULL DEFAULT 0 COMMENT '本周完成阅读VDO首页视频的数量' ,
`newWordsWeek`  int(10) NULL DEFAULT 0 COMMENT '本周学习生词数量' ,
`finishChallengeWeek`  int(10) NULL DEFAULT 0 COMMENT '本周完成challenge关卡数量' ,
`averageScoreWeek`  int(10) NULL DEFAULT 0 COMMENT '本周challenge平均得分' ,
`studyTimeMax`  int(10) NULL DEFAULT 0 COMMENT '史上学习时长最久，精确到分钟' ,
`studyTimeMaxDate`  varchar(10) NULL DEFAULT '2018-01-01' COMMENT '达成史上学习时长最久的日期，2019-02-12' ,
`finishVDOAll`  int(10) NULL DEFAULT 0 COMMENT '完成阅读VDO首页视频的总数量' ,
`newWordsAll`  int(10) NULL DEFAULT 0 COMMENT '学习生词总数量' ,
`finishChallengeAll`  int(10) NULL DEFAULT 0 COMMENT '完成challenge关卡总数量' ,
`questionAmoutToday`  int(10) NULL DEFAULT 0 COMMENT '今日做题目数量' ,
`questionAmoutAll`  int(10) NULL DEFAULT 0 COMMENT '总做题目数量' ,
PRIMARY KEY (`id`)
)
    COMMENT '用户学习数据表 '
    CHARSET utf8;

CREATE TABLE `ed_user_value` (
`id`  int(20) NOT NULL COMMENT '用户唯一id' ,
`getBambooToday`  int(20) NULL DEFAULT 0 COMMENT '今日获得竹子数量' ,
`useBambooToday`  int(20) NULL DEFAULT 0 COMMENT '今日消耗竹子数量' ,
`getBambooAll`  int(20) NULL DEFAULT 0 COMMENT '总获得竹子数量，即累计获得上限' ,
`useBambooAll`  int(20) NULL DEFAULT 0 COMMENT '总消耗竹子数量，即累积消耗上限' ,
`getExpToday`  int(20) NULL DEFAULT 0 COMMENT '今日获得经验值' ,
`exp`  int(50) NULL DEFAULT 0 COMMENT '总经验值' ,
`expRank`  int(20) NULL DEFAULT 9999 COMMENT '经验榜排名' ,
`expRankLastweek`  int(20) NULL DEFAULT 9999 COMMENT '经验榜上周排名' ,
`bamboo`  int(20) NULL DEFAULT 0 COMMENT '竹子数量' ,
`bambooRank`  int(20) NULL DEFAULT 9999 COMMENT '竹子榜今日排名' ,
`bambooRankLastweek`  int(20) NULL DEFAULT 9999 COMMENT '竹子榜上周排名' ,
PRIMARY KEY (`id`)
)
    COMMENT '用户数值表'
    CHARSET utf8;

CREATE TABLE `ed_user_index` (
`id`  int(20) NOT NULL COMMENT '用户唯一id' ,
`word`  text NULL COMMENT '生词本表中的主键数组' ,
`question`  text NULL COMMENT '错题本表中的主键数组' ,
`favourite`  text NULL COMMENT '收藏夹，包含VDO id列表。如果在试卷的阅读页也收藏了视频，也会被加入收藏夹' ,
`download`  text NULL COMMENT '下载目录，包含本地下载的VDO id列表。如果在试卷的阅读页也下载了视频，也会被加入下载目录' ,
PRIMARY KEY (`id`)
)
    COMMENT '用户功能索引表'
    CHARSET utf8;

    CREATE TABLE `ed_user_map` (
`id`  int(20) NOT NULL COMMENT '用户唯一id' ,
`s1mapScore`  text NULL COMMENT 'S1按照路点mapid顺序，每个路点的用户真实得分' ,
`s2mapScore`  text NULL COMMENT 'S2按照路点mapid顺序，每个路点的用户真实得分' ,
`s3mapScore`  text NULL COMMENT 'S3按照路点mapid顺序，每个路点的用户真实得分' ,
`s4mapScore`  text NULL COMMENT 'S4按照路点mapid顺序，每个路点的用户真实得分' ,
`s5mapScore`  text NULL COMMENT 'S5按照路点mapid顺序，每个路点的用户真实得分' ,
`s6mapScore`  text NULL COMMENT 'S6按照路点mapid顺序，每个路点的用户真实得分' ,
PRIMARY KEY (`id`)
)
    COMMENT '用户路点表'
    CHARSET utf8;
CREATE TABLE `ed_user_exp_rank_list` (
`id`  int(11) NOT NULL  AUTO_INCREMENT ,
`userId`  int(11) NULL ,
`exp`  int(20) NULL ,
`type`  varchar(10) NULL DEFAULT 'day' COMMENT '类型 day 天  week 周 ' ,
`ranks`  int(10) NULL DEFAULT 9999 ,
`rankChange`  int(10) NULL DEFAULT 0 COMMENT '正为升，负为降' ,
PRIMARY KEY (`id`)
)
    COMMENT '经验排行榜'
    CHARSET utf8;

CREATE TABLE `ed_user_bamboo_rank_list` (
        `id`  int(11) NOT NULL   AUTO_INCREMENT,
        `userId`  int(11) NULL ,
        `bamboo`  int(20) NULL ,
        `type`  varchar(10) NULL DEFAULT 'day' COMMENT '类型 day 天  week 周 ' ,
        `ranks`  int(10) NULL DEFAULT 9999 ,
        `rankChange`  int(10) NULL DEFAULT 0  COMMENT '正为升，负为降' ,
        PRIMARY KEY (`id`)
)
    COMMENT '竹子排行榜'
    CHARSET utf8;

ALTER TABLE `ed_user_map`
ADD COLUMN `isfirst`  int(2) NULL DEFAULT 0 COMMENT 'o 首次进入  1非首次' AFTER `s6mapScore`,
ADD COLUMN `unblock`  varchar(50) NULL DEFAULT '' COMMENT '解锁 串 ' AFTER `isfirst`;

ALTER TABLE `ed_user_value`
ADD COLUMN `sumWeekExp`  int(20) NULL DEFAULT 0 AFTER `bambooRankLastweek`,
ADD COLUMN `sumWeekBamboo`  int(20) NULL DEFAULT 0 COMMENT '当周总获取竹子' AFTER `sumWeekExp`,
ADD COLUMN `lastExpRank`  int(20) NULL DEFAULT 9999 COMMENT '上次经验排名（前天）' AFTER `sumWeekBamboo`,
ADD COLUMN `lastExpWeekRank`  int(20) NULL DEFAULT 9999 COMMENT '上次经验排名（上上周）' AFTER `lastExpRank`,
ADD COLUMN `lastBambooRank`  int(20) NULL DEFAULT 9999 COMMENT '上次竹子排名（前天）' AFTER `lastExpWeekRank`,
ADD COLUMN `lastBambooWeekRank`  int(20) NULL DEFAULT 999 AFTER `lastBambooRank`;


CREATE TABLE `ed_exercise_answer` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`eid`  int(11) NULL DEFAULT NULL COMMENT '用户id' ,
`uid`  int(11) NULL DEFAULT NULL ,
`vid`  int(11) NULL DEFAULT NULL COMMENT '视频id' ,
`user_answer`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户答案' ,
`create_time`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提交时间' ,
PRIMARY KEY (`id`)
)
    COMMENT '习题答案表！'
    CHARSET utf8;

ALTER TABLE `ed_exam_user_info`
ADD COLUMN `type`  int(5) NULL DEFAULT 1 COMMENT '1=未考试，2=首考未提交，3=首考并提交，4=重考1阶未提交，5=重考1阶并提交，6=重考2阶未提交，7=重考2阶并提交，8=重新考试，9=其他情况' AFTER `score`,
ADD COLUMN `isReset`  int(2) NULL DEFAULT 1 COMMENT '0 未重考给答案  1重考不给答案' AFTER `type`;

ALTER TABLE `ed_user_value`
ADD COLUMN `getExpYesterday`  int(20) NULL DEFAULT 0 COMMENT '昨日经验' AFTER `lastBambooWeekRank`,
ADD COLUMN `getBambooYesterday`  int(20) NULL DEFAULT 0 COMMENT '昨日经验' AFTER `getExpYesterday`;

CREATE TABLE ed_base_code (
    code        VARCHAR(20)   NOT NULL,
    status      INT DEFAULT 0 NOT NULL COMMENT '状态：0.未使用 1.已使用（已使用的code会关联到合作方code码中）',
    create_time VARCHAR(20)   NULL COMMENT '生成时间',
    CONSTRAINT ed_base_code_code_uindex
        UNIQUE (code)
)
    COMMENT 'code码库';

ALTER TABLE ed_base_code
    ADD PRIMARY KEY (code);

ALTER TABLE ed_partner_code MODIFY identify_code VARCHAR(50) NOT NULL COMMENT '验证码';

CREATE UNIQUE INDEX ed_partner_code_identify_code_uindex
    ON ed_partner_code (identify_code);


CREATE TABLE `ed_user_record` (
`id`  varchar(255) NOT NULL ,
`userId`  int(20) NULL ,
`type`  varchar(10) NULL DEFAULT 'exp' COMMENT 'exp   bamboo' ,
`targetValue`  int(255) NULL DEFAULT 0 COMMENT '目标值' ,
`beginValue`  int(255) NULL DEFAULT 0 COMMENT '开始值' ,
`changeValue`  int(255) NULL DEFAULT 0 ,
`createTime`  varchar(19) NULL ,
`isTake`  int(2) NULL DEFAULT 0 COMMENT ' 0未领取  1 已领取' ,
`content`  varchar(255) NULL COMMENT '内容' ,
`sourceId`  varchar(255) NULL,
PRIMARY KEY (`id`)
)
  COMMENT '经验竹子流水表'
    CHARSET utf8;

DROP TABLE ed_user_authcode;

DROP TABLE ed_user_browse;

alter table ed_prop
    add prop_name_hk VARCHAR(20) null comment '道具名称繁体';
alter table ed_prop
    add prop_name_en VARCHAR(20) null comment '道具名称英文';

ALTER TABLE `ed_wrong_topic`
ADD COLUMN `type`  varchar(50) NULL DEFAULT '' COMMENT '题型',
ADD COLUMN `videoId`  int(20) NULL DEFAULT NULL COMMENT '视频ID' AFTER `addDate`;

ALTER TABLE `ed_feedback`
ADD COLUMN `sourceType`  int(3) NULL DEFAULT 1 COMMENT '1视频问题，2试卷问题 ,3其他' AFTER `status`,
ADD COLUMN `questionType`  int(3) NULL DEFAULT 1 COMMENT '平台 1 iphone，2ipad，3androidPhone，4androidPad，5PC' AFTER `sourceType`;

ALTER TABLE yj_event_info
    DROP COLUMN sign_up_json;

alter table yj_event_info
    add description_pic_url VARCHAR(200) null comment '比赛简介视频封面图';

alter table yj_user_bm_info
    add teacher_user_id VARCHAR(10) null comment '老师用户ID';

alter table ed_user
    add orderMap VARCHAR(50) null comment '开通级别：S1,S2(多个以逗号隔开)';

ALTER TABLE `ed_user_study`
ADD COLUMN `weekHour`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '记录本周每天学习时长（20，50，60，80，10）/H' AFTER `questionAmoutAll`;

ALTER TABLE `ed_collect`
ADD COLUMN `mapId`  int(20) NULL AFTER `type`,
ADD COLUMN `examId`  varchar(255) NULL AFTER `mapId`;

alter table yj_school
    add school_type INT null comment '说明：中学：1、小学：2、中小学：3';

alter table ed_partner_class
    add end_time VARCHAR(20) null comment '结束时间（作用班级合作时间）';
alter table ed_partner_class
    add start_time VARCHAR(20) null comment '开始时间（作用班级合作时间）';

CREATE TABLE `ed_help` (
`id`  int(20) NOT NULL AUTO_INCREMENT ,
`question`  varchar(255) NULL DEFAULT '' ,
`answersJson`  text NULL ,
PRIMARY KEY (`id`)
)
  COMMENT '帮助列表';

alter table yj_event_info
    add description_video_url VARCHAR(200) null;

CREATE TABLE `ed_exam_comment` (
`id`  int(20) NOT NULL AUTO_INCREMENT ,
`userId`  int(20) NULL ,
`examId` varchar(255) NULL ,
`createTime`  varchar(30) NULL ,
`content`  varchar(300) NULL DEFAULT '' COMMENT '内容' ,
`praise`  int(20) NULL DEFAULT 0 COMMENT '点赞数' ,
PRIMARY KEY (`id`)
)
  COMMENT '试卷评论表';

  CREATE TABLE `ed_exam_comment_praise` (
`id`  int(20) NOT NULL AUTO_INCREMENT ,
`commentId`  int(20) NULL ,
`userId`  int(20) NULL ,
PRIMARY KEY (`id`)
)
;
CREATE TABLE `ed_app_version` (
`id`  int(20) NOT NULL AUTO_INCREMENT ,
`type`  int(2) NULL DEFAULT 1 COMMENT '1=IOS 或 2=ANDROID' ,
`newVersion`  varchar(20) NULL COMMENT '最新版本号' ,
`apkUrl`  varchar(255) NULL COMMENT '下载URL' ,
`updateDescription`  varchar(255) NULL COMMENT '更新文案 ',
`forceUpdate`  int(2) NULL DEFAULT 0 COMMENT '1强制更新，0不用' ,
PRIMARY KEY (`id`)
)
  COMMENT 'app版本控制';

ALTER TABLE `ed_exam_user_info`
ADD COLUMN `useTime`  varchar(20) NULL DEFAULT '0' AFTER `isReset`;

alter table ed_feedback
    add processingTime VARCHAR(20) null comment '处理时间';

alter table ed_feedback
    add user_id int null comment '用户ID';
alter table ed_feedback modify status int default 0 not null comment '处理状态：0.待处理 1.已处理 2.处理中';


CREATE TABLE `ed_system_message` (
`id`  int(20) NOT NULL AUTO_INCREMENT ,
`title`  varchar(255) NULL ,
`context`  text NULL ,
`type`  int(2) NULL DEFAULT 1 COMMENT '1=短内容；2=长内容；3=图文内容;4链接形式' ,
`createTime`  varchar(20) NULL ,
PRIMARY KEY (`id`)
)
;

alter table ed_cms_user
    add gender VARCHAR(10) default 'male' null comment '性别：男：male  女：female';

alter table ed_cms_user
    add birthday VARCHAR(20) null comment '生日，2019-02-12';

alter table ed_cms_user
    add contact VARCHAR(50) null comment '联系方式';

alter table ed_cms_user
    add teacherUserId VARCHAR(10) null comment '老师角色APP账号ID';

alter table ed_cms_user
    add schoolId VARCHAR(10) null comment '学校负责人关联合作方学校ID';

alter table ed_partner_code
    add studentName VARCHAR(100) null comment '学生名称';
alter table ed_partner_code
    add gender VARCHAR(10) default 'male' null comment '性别：男->male 女->female';

alter table ed_word_pk_record
    add is_receive int default 0 not null comment '领取竹子状态：1.领取 0.未领取';

alter table ed_partner_class
    add grade int null comment '年级';

  ALTER TABLE `ed_answer`
ADD COLUMN `createTime`  varchar(20) NULL;
CREATE TABLE `ed_firstExam_user` (
`id`  int(20) NOT NULL AUTO_INCREMENT ,
`userId`  int(20) NULL ,
`firstScore`  int(20) NULL DEFAULT 0 ,
`comprehension`  int(20) NULL DEFAULT 0 ,
`vocabulary`  int(20) NULL DEFAULT 0 ,
`grammar`  int(20) NULL DEFAULT 0 ,
`highestScore`  int(20) NULL DEFAULT 0 ,
`nodeNum`  int(10) NULL DEFAULT 0 ,
`examId`  varchar(255) NULL ,
`level`  varchar(20) NULL ,
PRIMARY KEY (`id`)
)
;
ALTER TABLE `ed_firstExam_user`
ADD COLUMN `ranking`  int(20) NULL DEFAULT 1 COMMENT '班级排名' AFTER `level`,
ADD COLUMN `classId`  int(20) NULL AFTER `ranking`;

ALTER TABLE `ed_firstExam_user`
ADD COLUMN `highestComprehension`  int(20) NULL,
ADD COLUMN `highestVocabulary`  int(20) NULL,
ADD COLUMN `highestGrammar`  int(20) NULL;


ALTER TABLE ed_pet
    ADD level INT DEFAULT 1 NULL COMMENT '宠物等级';


    ALTER TABLE `ed_app_version`
ADD COLUMN `forceVersion`  varchar(255) NULL COMMENT '强制更新到版本号';

alter table yj_school
    add distinct_hk VARCHAR(100) null comment '繁体地区';
ALTER TABLE `ed_exam_user_info`
ADD COLUMN `flag`  int(1) NULL DEFAULT 0 COMMENT '标记是否第一次进入重考一阶';




alter table ed_goods
    add new_badge int default 0 not null comment '新品角标设置：0.默认 1.勾选';

alter table ed_goods
    add hot_badge int default 0 not null comment '热卖角标设置：0.默认 1.勾选';

alter table ed_goods
    add activity_badge int default 0 not null comment '活动角标设置：0.默认 1.勾选';

alter table ed_goods
    add discount_badge int default 0 not null comment '折扣角标设置：0.默认 1.勾选';

CREATE TABLE `ed_user_sign` (
  `user_id` bigint(20) NOT NULL,
  `count` int(11) DEFAULT NULL comment '签到次数',
  `create_time` varchar(20) DEFAULT NULL comment '签到时间',
  `last_modify_time` varchar(10) DEFAULT NULL comment '最后签到时间',
  `sign_count` int(11) DEFAULT NULL comment '连续签到次数',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

ALTER TABLE `ed_map`
ADD COLUMN `webx`  int(4) NULL DEFAULT NULL comment 'web x坐标',
ADD COLUMN `weby`  int(4) NULL DEFAULT NULL;

CREATE TABLE `ed_sure_answer` (
`id`  int(22) NOT NULL AUTO_INCREMENT ,
`userId`  int(22) NULL ,
`answersJson`  text NULL ,
`examId`  varchar(60) NULL ,
`createTime`  varchar(25) NULL ,
PRIMARY KEY (`id`)
);
-- 首考错误集合
CREATE TABLE `ed_error_first_answer` (
`id`  int(22) NOT NULL AUTO_INCREMENT ,
`userId`  int(22) NULL ,
`answersJson`  text NULL ,
`examId`  varchar(60) NULL ,
`createTime`  varchar(25) NULL ,
PRIMARY KEY (`id`)
);


