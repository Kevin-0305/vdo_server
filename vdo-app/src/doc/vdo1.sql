/*
 Navicat Premium Data Transfer

 Source Server         : 106.52.102.224_vdo_dev
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 106.52.102.224:3306
 Source Schema         : vdo1

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 02/09/2019 15:36:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ed_advert
-- ----------------------------
DROP TABLE IF EXISTS `ed_advert`;
CREATE TABLE `ed_advert` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imgUrl` varchar(2000) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `adType` varchar(10) DEFAULT NULL,
  `addDate` varchar(100) DEFAULT NULL,
  `editor` varchar(100) DEFAULT NULL,
  `iosImgUrl` varchar(500) DEFAULT NULL,
  `anImgUrl` varchar(500) DEFAULT NULL,
  `linkUrl` varchar(500) DEFAULT NULL,
  `status` varchar(2) DEFAULT NULL,
  `iPhone4` varchar(500) DEFAULT NULL,
  `iPhone5` varchar(500) DEFAULT NULL,
  `iPhone6` varchar(500) DEFAULT NULL,
  `iPhone6Plus` varchar(500) DEFAULT NULL,
  `phoneX` varchar(255) DEFAULT NULL,
  `phoneXMax` varchar(255) DEFAULT NULL,
  `phoneXr` varchar(255) DEFAULT NULL,
  `ipad` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=123456828 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_answer
-- ----------------------------
DROP TABLE IF EXISTS `ed_answer`;
CREATE TABLE `ed_answer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `examId` varchar(200) NOT NULL,
  `userId` varchar(200) NOT NULL,
  `answersJson` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=260 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_back_user
-- ----------------------------
DROP TABLE IF EXISTS `ed_back_user`;
CREATE TABLE `ed_back_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `role` varchar(50) DEFAULT NULL,
  `create_time` varchar(50) DEFAULT NULL,
  `userImg` varchar(255) DEFAULT NULL,
  `vcode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_banner
-- ----------------------------
DROP TABLE IF EXISTS `ed_banner`;
CREATE TABLE `ed_banner` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imgUrl` varchar(2000) DEFAULT NULL COMMENT '图片路径',
  `title` varchar(500) DEFAULT NULL COMMENT '标题',
  `addDate` varchar(100) DEFAULT NULL COMMENT '添加时间',
  `editor` varchar(100) DEFAULT NULL COMMENT '操作人',
  `bgColor` varchar(200) DEFAULT NULL COMMENT '背景颜色',
  `linkUrl` varchar(500) DEFAULT NULL COMMENT 'banner链接地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20190010 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_banner_image
-- ----------------------------
DROP TABLE IF EXISTS `ed_banner_image`;
CREATE TABLE `ed_banner_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(2000) DEFAULT NULL COMMENT '标题',
  `link` varchar(100) DEFAULT NULL COMMENT '链接',
  `pc` varchar(200) DEFAULT NULL COMMENT 'pc banner',
  `iphone` varchar(200) DEFAULT NULL COMMENT 'iphone banner',
  `ipad` varchar(200) DEFAULT NULL COMMENT 'IPad banner',
  `android` varchar(200) DEFAULT NULL COMMENT '安卓 banner',
  `start_time` varchar(20) DEFAULT NULL COMMENT '开始时间',
  `end_time` varchar(20) DEFAULT NULL COMMENT '结束时间',
  `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(20) DEFAULT NULL COMMENT '更新人',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='banner';

-- ----------------------------
-- Table structure for ed_box
-- ----------------------------
DROP TABLE IF EXISTS `ed_box`;
CREATE TABLE `ed_box` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '宝箱ID',
  `box_type` varchar(200) DEFAULT NULL COMMENT '宝箱类型',
  `bamboo` varchar(10) DEFAULT NULL COMMENT '竹子数量',
  `icon_img` varchar(200) DEFAULT NULL COMMENT '图标',
  `box_img` varchar(200) DEFAULT NULL COMMENT '宝箱详图',
  `drop_json` varchar(2000) DEFAULT NULL COMMENT '掉落物品json',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='宝箱';

-- ----------------------------
-- Table structure for ed_class
-- ----------------------------
DROP TABLE IF EXISTS `ed_class`;
CREATE TABLE `ed_class` (
  `id` char(36) NOT NULL,
  `class_name` varchar(100) DEFAULT NULL COMMENT '班级名称',
  `classCode` varchar(50) DEFAULT NULL COMMENT '班级code',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态 0:未发布 1：已发布',
  `school_id` char(36) DEFAULT NULL COMMENT '学校id',
  `createAccount` varchar(50) DEFAULT NULL COMMENT '创建人',
  `createTime` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `updateAccount` varchar(50) DEFAULT NULL COMMENT '修改人',
  `updateTime` varchar(20) DEFAULT NULL COMMENT '修改时间',
  `class_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4D87E5D86FD7B304` (`school_id`),
  CONSTRAINT `ed_class_ibfk_1` FOREIGN KEY (`school_id`) REFERENCES `ed_school` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_cms_user
-- ----------------------------
DROP TABLE IF EXISTS `ed_cms_user`;
CREATE TABLE `ed_cms_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '姓名',
  `exec_count` int(11) DEFAULT NULL COMMENT '可评分次数',
  `account` varchar(100) NOT NULL COMMENT '账号',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态：0.未开通 1.已开通',
  `group_types` varchar(20) DEFAULT '0' COMMENT '评分组别权限：0.未开通 1.',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(50) DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` varchar(50) DEFAULT NULL COMMENT '修改时间',
  `role_type` int(2) DEFAULT NULL COMMENT '角色：1.超管 2.运营 3.老师 4.评委',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='后台用户表';

-- ----------------------------
-- Table structure for ed_collect
-- ----------------------------
DROP TABLE IF EXISTS `ed_collect`;
CREATE TABLE `ed_collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `vid` int(11) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1136 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_collect_word
-- ----------------------------
DROP TABLE IF EXISTS `ed_collect_word`;
CREATE TABLE `ed_collect_word` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `word` varchar(100) DEFAULT NULL COMMENT '收藏的单词',
  `create_time` varchar(50) DEFAULT NULL COMMENT '创建时间',
  `vocabulary` varchar(100) DEFAULT NULL,
  `pronunciation` varchar(100) DEFAULT NULL,
  `Englisth_Definition` varchar(1000) DEFAULT NULL,
  `Chinese_Definition` varchar(2000) DEFAULT NULL,
  `notes` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1037 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_column
-- ----------------------------
DROP TABLE IF EXISTS `ed_column`;
CREATE TABLE `ed_column` (
  `ColumnID` bigint(20) NOT NULL AUTO_INCREMENT,
  `Index` int(11) DEFAULT NULL,
  `ShowFont` varchar(50) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ColumnID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_comment
-- ----------------------------
DROP TABLE IF EXISTS `ed_comment`;
CREATE TABLE `ed_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vid` int(11) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `content` varchar(5000) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `userImg` varchar(1000) DEFAULT NULL,
  `nickname` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_email
-- ----------------------------
DROP TABLE IF EXISTS `ed_email`;
CREATE TABLE `ed_email` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `subject` varchar(2000) DEFAULT NULL COMMENT '邮件主题',
  `content` text COMMENT '邮件内容',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(20) DEFAULT NULL COMMENT '修改人',
  `addressees` varchar(2000) DEFAULT NULL COMMENT '收件人',
  `status` int(11) DEFAULT '0' COMMENT '状态：0.未发送 1.已发送 2.等待发送',
  `send_time` varchar(20) DEFAULT NULL COMMENT '发送时间',
  `attachment_json` varchar(3000) DEFAULT NULL COMMENT '附件json',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='邮件信息';

-- ----------------------------
-- Table structure for ed_enroll_info
-- ----------------------------
DROP TABLE IF EXISTS `ed_enroll_info`;
CREATE TABLE `ed_enroll_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(200) NOT NULL COMMENT '用户名称',
  `contact_way` varchar(200) NOT NULL COMMENT '报名用户联系方式',
  `event_id` int(11) NOT NULL COMMENT '活动主键ID',
  `eventName` varchar(255) DEFAULT NULL COMMENT '活动名称',
  `addDate` datetime DEFAULT NULL COMMENT '登记时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=152 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_event
-- ----------------------------
DROP TABLE IF EXISTS `ed_event`;
CREATE TABLE `ed_event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(500) DEFAULT NULL,
  `imgUrl` varchar(2000) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `browse` int(11) DEFAULT NULL,
  `praise` int(11) DEFAULT NULL,
  `collect` int(11) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  `eventUrl` varchar(2000) DEFAULT NULL,
  `downloadUrl` varchar(2000) DEFAULT NULL,
  `isRelease` int(11) DEFAULT NULL,
  `editor` varchar(100) DEFAULT NULL,
  `addDate` varchar(100) DEFAULT NULL,
  `isOpen` varchar(2) DEFAULT '0' COMMENT '是否开放（0登录可看，1不登录可看）',
  `cloud_url` varchar(2000) DEFAULT '' COMMENT '国内云视频服务器地址',
  `event_thumb` varchar(2000) DEFAULT '' COMMENT '缩略图',
  `time_length` varchar(20) DEFAULT '00:00' COMMENT '视频时长',
  `lastEditor` varchar(50) DEFAULT '' COMMENT '最后修改人',
  `modify_time` varchar(50) DEFAULT '' COMMENT '修改时间',
  `event_thumb_bigger` varchar(255) DEFAULT NULL,
  `level` varchar(64) DEFAULT '0' COMMENT '试题难度级别',
  `isQuiz` varchar(64) DEFAULT '0' COMMENT '是否有测试',
  `editorPick` varchar(64) DEFAULT '0' COMMENT '每日精选',
  `hot` varchar(64) DEFAULT '0' COMMENT '热门推荐',
  `content` longtext,
  `share_url` varchar(2000) DEFAULT '' COMMENT '分享链接',
  `ipadImg_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=621 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_event_user_info
-- ----------------------------
DROP TABLE IF EXISTS `ed_event_user_info`;
CREATE TABLE `ed_event_user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nameCh` varchar(200) DEFAULT NULL COMMENT '中文名',
  `nameEn` varchar(200) DEFAULT NULL COMMENT '英文名',
  `sex` int(11) DEFAULT NULL COMMENT '1.男 2.女',
  `birthday` varchar(200) DEFAULT NULL COMMENT '生日',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `contactNo` varchar(200) DEFAULT NULL COMMENT '联系人号码',
  `school` varchar(200) DEFAULT NULL COMMENT '学校',
  `content` text COMMENT '内容',
  `schoolClass` varchar(200) DEFAULT NULL COMMENT '班级',
  `addDate` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='活动用户信息';

-- ----------------------------
-- Table structure for ed_exam_info
-- ----------------------------
DROP TABLE IF EXISTS `ed_exam_info`;
CREATE TABLE `ed_exam_info` (
  `id` varchar(200) NOT NULL,
  `setter` varchar(32) DEFAULT NULL COMMENT '作者',
  `title` varchar(500) NOT NULL COMMENT '试卷标题',
  `level` varchar(8) DEFAULT NULL COMMENT '等级',
  `exam_type` varchar(300) DEFAULT NULL COMMENT '试卷类型',
  `video_id` bigint(20) DEFAULT NULL COMMENT '视频id',
  `question_num` int(11) DEFAULT NULL COMMENT '试题数',
  `comprehension_num` int(11) DEFAULT NULL COMMENT '阅读理解题数',
  `vocabulary_num` int(11) DEFAULT NULL COMMENT '词汇题数',
  `grammar_num` int(11) DEFAULT NULL COMMENT 'vocabularyNum',
  `partListJson` text COMMENT 'vocabularyNum',
  `exam_pdf` varchar(300) DEFAULT NULL COMMENT '试卷pdf',
  `answer_pdf` varchar(300) DEFAULT NULL COMMENT '答案Pdf',
  `banner_pic` varchar(300) DEFAULT NULL COMMENT '显示图片',
  `questions_id` varchar(2000) DEFAULT NULL,
  `exam_text` text COMMENT '试题编辑器内容',
  `create_time` varchar(200) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(200) DEFAULT NULL COMMENT '更新时间',
  `create_account` varchar(200) DEFAULT NULL COMMENT '创建人',
  `update_account` varchar(200) DEFAULT NULL COMMENT '更新人',
  `state` int(11) NOT NULL DEFAULT '0' COMMENT '0.未发布 1.发布',
  `userIsSbmit` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `vid` varchar(255) DEFAULT NULL,
  `userIsSubmit` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='试卷信息表';

-- ----------------------------
-- Table structure for ed_exam_record
-- ----------------------------
DROP TABLE IF EXISTS `ed_exam_record`;
CREATE TABLE `ed_exam_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eid` int(11) DEFAULT NULL COMMENT '用户id',
  `uid` int(11) DEFAULT NULL,
  `vid` int(11) DEFAULT NULL COMMENT '视频id',
  `user_answer` varchar(2) DEFAULT NULL COMMENT '用户答案',
  `create_time` varchar(50) DEFAULT NULL COMMENT '提交时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8925 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_exam_user_info
-- ----------------------------
DROP TABLE IF EXISTS `ed_exam_user_info`;
CREATE TABLE `ed_exam_user_info` (
  `id` varchar(200) DEFAULT NULL COMMENT '主键',
  `sbmit_json` text COMMENT '用户做题JSON',
  `examInfo_id` varchar(200) DEFAULT NULL COMMENT '试卷ID',
  `user_id` varchar(200) DEFAULT NULL,
  `answer_id` bigint(20) DEFAULT NULL COMMENT '答案 id',
  `retry_time` int(11) DEFAULT NULL COMMENT '重试提交次数',
  `create_time` varchar(200) DEFAULT NULL,
  `update_time` varchar(200) DEFAULT NULL,
  `score` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_exercise
-- ----------------------------
DROP TABLE IF EXISTS `ed_exercise`;
CREATE TABLE `ed_exercise` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `vid` int(11) DEFAULT NULL COMMENT '视频id',
  `question` varchar(1000) DEFAULT NULL COMMENT '问题',
  `chooseA` varchar(1000) DEFAULT NULL COMMENT '选项A',
  `chooseB` varchar(1000) DEFAULT NULL COMMENT '选项B',
  `chooseC` varchar(1000) DEFAULT NULL COMMENT '选项C',
  `chooseD` varchar(1000) DEFAULT NULL COMMENT '选项D',
  `answer` varchar(5) DEFAULT NULL COMMENT '答案选项',
  `explan` varchar(1000) DEFAULT NULL COMMENT '答案解释',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3689 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_feedback
-- ----------------------------
DROP TABLE IF EXISTS `ed_feedback`;
CREATE TABLE `ed_feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_json` varchar(2000) DEFAULT NULL COMMENT '附件json',
  `contact` varchar(200) DEFAULT NULL COMMENT '联系方式',
  `content` text COMMENT '反馈内容',
  `create_time` varchar(20) DEFAULT NULL COMMENT '提交时间',
  `status` int(11) DEFAULT NULL COMMENT '处理状态：0.待处理 1.已处理 2.处理中',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='问题反馈';

-- ----------------------------
-- Table structure for ed_goods
-- ----------------------------
DROP TABLE IF EXISTS `ed_goods`;
CREATE TABLE `ed_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `prop_id` int(11) DEFAULT NULL COMMENT '道具ID',
  `sort_no` varchar(10) DEFAULT NULL COMMENT '序号',
  `goods_badge` varchar(10) DEFAULT NULL COMMENT '商品角标',
  `discount` double DEFAULT NULL COMMENT '折扣',
  `price` double DEFAULT NULL COMMENT '商品原价',
  `discount_price` double DEFAULT NULL COMMENT '折后价',
  `status` int(11) DEFAULT '0' COMMENT '状态：0.默认 1.上架 2.下架',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(20) DEFAULT NULL COMMENT '更新人',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='商品';

-- ----------------------------
-- Table structure for ed_launch_image
-- ----------------------------
DROP TABLE IF EXISTS `ed_launch_image`;
CREATE TABLE `ed_launch_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(2000) DEFAULT NULL COMMENT '标题',
  `link` varchar(100) DEFAULT NULL COMMENT '链接',
  `iphone` varchar(200) DEFAULT NULL COMMENT 'iphone启动图',
  `ipad` varchar(200) DEFAULT NULL COMMENT 'IPad启动图',
  `android` varchar(200) DEFAULT NULL COMMENT '安卓启动图',
  `android_pad` varchar(200) DEFAULT NULL COMMENT '安卓pad',
  `start_time` varchar(20) DEFAULT NULL COMMENT '开始时间',
  `end_time` varchar(20) DEFAULT NULL COMMENT '结束时间',
  `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(20) DEFAULT NULL COMMENT '更新人',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='启动图';

-- ----------------------------
-- Table structure for ed_map
-- ----------------------------
DROP TABLE IF EXISTS `ed_map`;
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

-- ----------------------------
-- Table structure for ed_node
-- ----------------------------
DROP TABLE IF EXISTS `ed_node`;
CREATE TABLE `ed_node` (
  `node_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `part_id` bigint(20) NOT NULL,
  `question_id` bigint(20) DEFAULT NULL,
  `text` text,
  `type` varchar(10) DEFAULT NULL,
  `choose_type` varchar(5) DEFAULT NULL,
  `title_text` text,
  `title_img` varchar(500) DEFAULT NULL,
  `option_list_json` text,
  `img_fill_content_list_json` text,
  `number` bigint(20) DEFAULT NULL,
  `left_json` text,
  `right_json` text,
  `answer` varchar(200) DEFAULT NULL,
  `orders` int(11) NOT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_account` int(11) DEFAULT NULL COMMENT '创建人',
  `update_account` int(11) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`node_id`),
  KEY `idx_part_id` (`part_id`)
) ENGINE=InnoDB AUTO_INCREMENT=276 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_order
-- ----------------------------
DROP TABLE IF EXISTS `ed_order`;
CREATE TABLE `ed_order` (
  `orderId` varchar(50) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `order_status` int(11) DEFAULT NULL,
  `paymentWay` int(11) DEFAULT NULL,
  `product_id` varchar(255) DEFAULT NULL,
  `totalPrice` decimal(19,2) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `productName` varchar(255) DEFAULT NULL,
  `tradeNo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`orderId`),
  KEY `FK4E33C3AE7493EAD0` (`product_id`),
  CONSTRAINT `ed_order_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `ed_product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_order_log
-- ----------------------------
DROP TABLE IF EXISTS `ed_order_log`;
CREATE TABLE `ed_order_log` (
  `id` char(32) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `create_date` varchar(255) DEFAULT NULL,
  `orderId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_part
-- ----------------------------
DROP TABLE IF EXISTS `ed_part`;
CREATE TABLE `ed_part` (
  `part_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `exam_info_id` varchar(200) NOT NULL,
  `part_name` text,
  `orders` int(11) NOT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_account` int(11) DEFAULT NULL COMMENT '创建人',
  `update_account` int(11) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`part_id`),
  KEY `idx_exam_info_id` (`exam_info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_path
-- ----------------------------
DROP TABLE IF EXISTS `ed_path`;
CREATE TABLE `ed_path` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `exam_id` varchar(200) DEFAULT NULL,
  `orders` bigint(20) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_exam_id` (`exam_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_product
-- ----------------------------
DROP TABLE IF EXISTS `ed_product`;
CREATE TABLE `ed_product` (
  `id` char(200) NOT NULL,
  `name` varchar(20) DEFAULT NULL COMMENT '商品名称',
  `price` decimal(10,2) DEFAULT NULL COMMENT '价格',
  `sale_price` decimal(10,2) DEFAULT NULL COMMENT '销售价格',
  `buckle` varchar(50) DEFAULT NULL COMMENT '则扣',
  `month` int(11) DEFAULT NULL COMMENT 'vip月份',
  `detail` varchar(500) DEFAULT NULL COMMENT '商品详情',
  `create_account` varchar(255) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  `update_account` varchar(255) DEFAULT NULL,
  `update_time` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT '1' COMMENT '1.正常 0.删除',
  `hkDetail` varchar(255) DEFAULT NULL,
  `hkName` varchar(255) DEFAULT NULL,
  `zhDetail` varchar(255) DEFAULT NULL,
  `zhName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_prop
-- ----------------------------
DROP TABLE IF EXISTS `ed_prop`;
CREATE TABLE `ed_prop` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `image_url` varchar(200) DEFAULT NULL COMMENT '道具图片',
  `prop_name` varchar(200) DEFAULT NULL COMMENT '道具名称',
  `price` varchar(200) DEFAULT NULL COMMENT '道具价格',
  `bamboo` varchar(200) DEFAULT NULL COMMENT '获得竹子',
  `exp` varchar(200) DEFAULT NULL COMMENT '经验',
  `description` varchar(2000) DEFAULT NULL COMMENT '道具描述',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
  `update_time` varchar(20) DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='道具';

-- ----------------------------
-- Table structure for ed_questions
-- ----------------------------
DROP TABLE IF EXISTS `ed_questions`;
CREATE TABLE `ed_questions` (
  `id` varchar(200) NOT NULL,
  `title` varchar(200) DEFAULT NULL,
  `sum_count` int(11) DEFAULT NULL COMMENT '题目总分',
  `type` int(11) DEFAULT NULL,
  `select5w_Json` text COMMENT '5W选择题',
  `option_Json` text COMMENT '选择题',
  `fillout_Json` text COMMENT '填空题',
  `graphic_Json` text COMMENT '图文匹配题',
  `filloutSent_Json` text COMMENT '填写句子',
  `matching_Json` text COMMENT '连线题',
  `create_time` varchar(200) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(200) DEFAULT NULL COMMENT '更新时间',
  `create_account` varchar(200) DEFAULT NULL COMMENT '创建人',
  `update_account` varchar(200) DEFAULT NULL COMMENT '更新人',
  `jsonObj` tinyblob,
  `web_title` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='试题表';

-- ----------------------------
-- Table structure for ed_school
-- ----------------------------
DROP TABLE IF EXISTS `ed_school`;
CREATE TABLE `ed_school` (
  `id` char(36) NOT NULL,
  `school_name` varchar(50) DEFAULT NULL COMMENT '学校名称',
  `school_code` varchar(50) DEFAULT NULL COMMENT '学校code',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态0:未发布 1:已发布',
  `student_code` varchar(500) DEFAULT NULL COMMENT '学生code',
  `teacher_code` varchar(500) DEFAULT NULL COMMENT '老师code',
  `createAccount` varchar(50) DEFAULT NULL,
  `createTime` varchar(20) DEFAULT NULL,
  `updateAccount` varchar(50) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `endTime` varchar(255) DEFAULT NULL,
  `startTime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_student_class
-- ----------------------------
DROP TABLE IF EXISTS `ed_student_class`;
CREATE TABLE `ed_student_class` (
  `id` char(36) NOT NULL,
  `userId` char(36) DEFAULT NULL COMMENT '学生id',
  `classId` char(36) DEFAULT NULL COMMENT '班级id',
  `createTime` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_student_report
-- ----------------------------
DROP TABLE IF EXISTS `ed_student_report`;
CREATE TABLE `ed_student_report` (
  `userId` varchar(100) NOT NULL,
  `video_number` int(10) DEFAULT NULL,
  `words_collected_number` int(10) DEFAULT NULL,
  `quiz_done` int(10) DEFAULT NULL,
  `correct` int(10) DEFAULT NULL,
  `correctRate` varchar(20) DEFAULT NULL,
  `video_browsing_time` int(11) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_subtitle
-- ----------------------------
DROP TABLE IF EXISTS `ed_subtitle`;
CREATE TABLE `ed_subtitle` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `video_id` bigint(20) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `beginTime` varchar(50) DEFAULT NULL,
  `endTime` varchar(50) DEFAULT NULL,
  `english` varchar(1000) DEFAULT NULL,
  `simp_chinese` varchar(1000) DEFAULT NULL,
  `trad_chinese` varchar(1000) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `timeSs` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=78775 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_subtitles
-- ----------------------------
DROP TABLE IF EXISTS `ed_subtitles`;
CREATE TABLE `ed_subtitles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vid` int(11) DEFAULT NULL,
  `beginTime` varchar(50) DEFAULT NULL,
  `endTime` varchar(50) DEFAULT NULL,
  `English` varchar(1000) DEFAULT NULL,
  `Chinese` varchar(1000) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `timeSs` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=78539 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_suggest
-- ----------------------------
DROP TABLE IF EXISTS `ed_suggest`;
CREATE TABLE `ed_suggest` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `useraccount` varchar(50) DEFAULT NULL,
  `suggest` text,
  `date` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_teacher_class
-- ----------------------------
DROP TABLE IF EXISTS `ed_teacher_class`;
CREATE TABLE `ed_teacher_class` (
  `id` char(36) NOT NULL,
  `userId` char(36) DEFAULT NULL COMMENT '老师id',
  `classId` char(36) DEFAULT NULL COMMENT '班级id',
  `createTime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_user
-- ----------------------------
DROP TABLE IF EXISTS `ed_user`;
CREATE TABLE `ed_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `useraccount` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `nickname` varchar(200) DEFAULT NULL,
  `userImg` varchar(1000) DEFAULT NULL,
  `facebookUID` varchar(200) DEFAULT NULL,
  `create_time` varchar(50) DEFAULT '' COMMENT '注册时间',
  `location` varchar(1000) DEFAULT '' COMMENT '位置',
  `user_type` varchar(255) DEFAULT NULL,
  `vip_enddate` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1120 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_user_authcode
-- ----------------------------
DROP TABLE IF EXISTS `ed_user_authcode`;
CREATE TABLE `ed_user_authcode` (
  `userId` varchar(20) NOT NULL,
  `code` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_user_browse
-- ----------------------------
DROP TABLE IF EXISTS `ed_user_browse`;
CREATE TABLE `ed_user_browse` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `useraccount` varchar(50) DEFAULT NULL,
  `browseDate` date DEFAULT NULL,
  `vid` int(11) DEFAULT NULL,
  `dateTime` varchar(50) DEFAULT '' COMMENT '时间',
  `browseTimes` int(50) DEFAULT '0' COMMENT '观看时长',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49247 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_version
-- ----------------------------
DROP TABLE IF EXISTS `ed_version`;
CREATE TABLE `ed_version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version_package` varchar(200) DEFAULT NULL,
  `version_code` varchar(500) DEFAULT NULL,
  `version_name` varchar(500) DEFAULT NULL,
  `version_desc` varchar(2000) DEFAULT NULL,
  `version_path` varchar(1000) DEFAULT NULL,
  `version_date` date DEFAULT NULL,
  `version_desc_en` varchar(1000) DEFAULT NULL,
  `version_desc_zh_hk` varchar(1000) DEFAULT NULL,
  `version_path_cn` varchar(1000) DEFAULT '' COMMENT '安卓apk内地路径',
  `type` varchar(10) DEFAULT NULL COMMENT '1强制更新，0非强制更新',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_video
-- ----------------------------
DROP TABLE IF EXISTS `ed_video`;
CREATE TABLE `ed_video` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(500) DEFAULT NULL,
  `imgUrl` varchar(2000) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `browse` int(11) DEFAULT NULL,
  `praise` int(11) DEFAULT NULL,
  `collect` int(11) DEFAULT NULL,
  `type` varchar(300) DEFAULT NULL,
  `videoUrl` varchar(2000) DEFAULT NULL,
  `downloadUrl` varchar(2000) DEFAULT NULL,
  `isRelease` int(11) DEFAULT NULL,
  `editor` varchar(100) DEFAULT NULL,
  `addDate` varchar(100) DEFAULT NULL,
  `isOpen` varchar(2) DEFAULT '0' COMMENT '是否开放（0登录可看，1不登录可看）',
  `cloud_url` varchar(2000) DEFAULT '' COMMENT '国内云视频服务器地址',
  `video_thumb` varchar(2000) DEFAULT '' COMMENT '缩略图',
  `time_length` varchar(20) DEFAULT '00:00' COMMENT '视频时长',
  `lastEditor` varchar(50) DEFAULT '' COMMENT '最后修改人',
  `modify_time` varchar(50) DEFAULT '' COMMENT '修改时间',
  `video_thumb_bigger` varchar(255) DEFAULT NULL,
  `level` varchar(300) DEFAULT '0' COMMENT '试题难度级别',
  `isQuiz` varchar(64) DEFAULT '0' COMMENT '是否有测试',
  `editorPick` varchar(64) DEFAULT '0' COMMENT '每日精选',
  `hot` varchar(64) DEFAULT '0' COMMENT '热门推荐',
  `pub_time` varchar(50) DEFAULT NULL COMMENT '自动发布日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1333 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_word
-- ----------------------------
DROP TABLE IF EXISTS `ed_word`;
CREATE TABLE `ed_word` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vid` int(11) DEFAULT NULL,
  `vocabulary` varchar(100) DEFAULT NULL,
  `pronunciation` varchar(100) DEFAULT NULL,
  `Englisth_Definition` varchar(1000) DEFAULT NULL,
  `Chinese_Definition` varchar(2000) DEFAULT NULL,
  `notes` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8188 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ed_word_pk
-- ----------------------------
DROP TABLE IF EXISTS `ed_word_pk`;
CREATE TABLE `ed_word_pk` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL COMMENT '标题',
  `explain_ch` varchar(200) DEFAULT NULL COMMENT '解释简体中文',
  `explain_hk` varchar(200) DEFAULT NULL COMMENT '解释繁体中文',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(20) DEFAULT NULL COMMENT '修改人',
  `level` varchar(10) DEFAULT NULL COMMENT '级别',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='单词PK';

-- ----------------------------
-- Table structure for yj_event_info
-- ----------------------------
DROP TABLE IF EXISTS `yj_event_info`;
CREATE TABLE `yj_event_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name_ch` varchar(500) DEFAULT NULL COMMENT '中文活动名称',
  `name_hk` varchar(500) DEFAULT NULL COMMENT '繁体活动名称',
  `name_en` varchar(500) DEFAULT NULL COMMENT '英文活动名称',
  `banner_url` varchar(200) DEFAULT NULL COMMENT 'banner图地址',
  `banner_square_url` varchar(200) DEFAULT NULL COMMENT '正方图地址',
  `sign_up_json` varchar(2000) DEFAULT NULL COMMENT '参赛须知',
  `pdf_json` varchar(2000) DEFAULT NULL COMMENT 'PDF文件信息',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(50) DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` varchar(50) DEFAULT NULL COMMENT '修改时间',
  `description_ch` varchar(2000) NOT NULL COMMENT '中文简介',
  `description_hk` varchar(2000) NOT NULL COMMENT '繁体简介',
  `description_en` varchar(2000) NOT NULL COMMENT '英文简介',
  `pic_json` text COMMENT '历届比赛图片',
  `video_json` text COMMENT '视频配置',
  `sponsors_json` text COMMENT '赞助商配置',
  `guests_json` text COMMENT '嘉宾配置',
  `players_json` text COMMENT '选手感言配置',
  `jury_organ_json` text COMMENT '评委结构配置',
  `status` int(11) DEFAULT '0' COMMENT '状态：0.默认 1.上架 2.下架',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='活动信息';

-- ----------------------------
-- Table structure for yj_event_news
-- ----------------------------
DROP TABLE IF EXISTS `yj_event_news`;
CREATE TABLE `yj_event_news` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title_ch` varchar(200) NOT NULL COMMENT '标题',
  `title_hk` varchar(200) DEFAULT NULL,
  `title_en` varchar(200) DEFAULT NULL,
  `content_hk` varchar(2000) DEFAULT NULL,
  `content_ch` varchar(2000) DEFAULT NULL COMMENT '内容',
  `content_en` varchar(2000) DEFAULT NULL,
  `event_id` varchar(100) NOT NULL COMMENT '活动ID',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(50) DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` varchar(50) DEFAULT NULL COMMENT '修改时间',
  `img_url` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='活动新闻';

-- ----------------------------
-- Table structure for yj_school
-- ----------------------------
DROP TABLE IF EXISTS `yj_school`;
CREATE TABLE `yj_school` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `distinct_en` varchar(100) DEFAULT NULL COMMENT '地区',
  `name_ch` varchar(100) DEFAULT NULL COMMENT '学校名称中文',
  `name_en` varchar(100) DEFAULT NULL COMMENT '学校名称英文',
  `distinct` varchar(100) DEFAULT NULL COMMENT '英文地区',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10974 DEFAULT CHARSET=utf8 COMMENT='演讲学校表';

-- ----------------------------
-- Table structure for yj_score_record
-- ----------------------------
DROP TABLE IF EXISTS `yj_score_record`;
CREATE TABLE `yj_score_record` (
  `id` int(10) DEFAULT NULL COMMENT 'ID',
  `user_id` varchar(10) DEFAULT NULL COMMENT '评分用户ID',
  `bm_id` varchar(10) DEFAULT NULL COMMENT '报名信息ID',
  `score` double DEFAULT NULL COMMENT '评分数',
  `create_time` varchar(20) DEFAULT NULL COMMENT '评分日期'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评分记录表';

-- ----------------------------
-- Table structure for yj_user_bm_info
-- ----------------------------
DROP TABLE IF EXISTS `yj_user_bm_info`;
CREATE TABLE `yj_user_bm_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_call` int(11) DEFAULT '0' COMMENT '老师称呼',
  `teacher_tel` varchar(100) DEFAULT NULL COMMENT '老师邮箱',
  `teacher_email` varchar(100) DEFAULT NULL COMMENT '老师邮箱',
  `teacher_name` varchar(100) DEFAULT NULL COMMENT '老师姓名',
  `school_area` varchar(100) DEFAULT NULL COMMENT '学校区域',
  `school_id` int(11) DEFAULT NULL COMMENT '学校',
  `name_ch` varchar(100) NOT NULL COMMENT '姓名中文',
  `name_en` varchar(100) DEFAULT NULL COMMENT '姓名英文',
  `sex` int(11) NOT NULL DEFAULT '0' COMMENT '状态：0.女 1.男',
  `grade` int(11) DEFAULT '0' COMMENT '年级',
  `group_type` int(11) DEFAULT NULL COMMENT '分组:1.初小 2.高小 3.初中 4.高中',
  `create_time` varchar(50) DEFAULT NULL COMMENT '创建时间',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮箱',
  `tel` varchar(50) DEFAULT NULL COMMENT '电话',
  `content` text COMMENT '演讲稿',
  `video_url` varchar(100) DEFAULT NULL COMMENT '视频链接',
  `type` int(11) DEFAULT '0' COMMENT '0.个人报名 1.老师报名',
  `upload_status` int(11) DEFAULT '0' COMMENT '上传状态：0.未上传 1.已上传 2.未上传视频 3.未上传演讲稿',
  `score_status` int(11) DEFAULT '0' COMMENT '评分状态：0.未评分 1.已评分',
  `score` varchar(10) DEFAULT NULL COMMENT '分数（由多个评委评分后的平均分）',
  `score_count` int(11) DEFAULT '0' COMMENT '评分次数',
  `event_id` varchar(10) DEFAULT NULL,
  `user_id` varchar(10) DEFAULT NULL COMMENT '用户ID',
  `register_code` varchar(20) DEFAULT NULL COMMENT '注册码',
  `lock_status` int(1) NOT NULL DEFAULT '0' COMMENT '评分加锁状态：0.正常 1.正在评分',
  `start_time` varchar(20) DEFAULT NULL COMMENT '开始时间',
  `end_time` varchar(20) DEFAULT NULL COMMENT '结束时间',
  `school_other_name` varchar(100) DEFAULT NULL COMMENT '其他学校名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='用户报名信息表';

SET FOREIGN_KEY_CHECKS = 1;
