/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : rac_management

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 17/05/2021 10:18:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_biz_entity
-- ----------------------------
DROP TABLE IF EXISTS `tb_biz_entity`;
CREATE TABLE `tb_biz_entity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `en_name` varchar(50) NOT NULL DEFAULT '' COMMENT '实体编码',
  `cn_name` varchar(50) NOT NULL DEFAULT '' COMMENT '中文名',
  `biz_line_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '业务线ID',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人ID',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `update_user_id` bigint(20) NOT NULL COMMENT '更新人ID',
  `deleted` int(2) NOT NULL DEFAULT '0' COMMENT '0 未删除 1 已删除',
  PRIMARY KEY (`id`),
  KEY `idx_en_name` (`biz_line_id`,`en_name`) USING BTREE COMMENT '英文名索引'
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='业务实体表';

-- ----------------------------
-- Records of tb_biz_entity
-- ----------------------------
BEGIN;
INSERT INTO `tb_biz_entity` VALUES (1, 'User', 'RAC用户实体', 1, '', '2020-12-15 12:46:44', 1, '2021-01-02 00:10:53', 1, 0);
INSERT INTO `tb_biz_entity` VALUES (2, 'Role', 'RAC角色实体', 1, '', '2020-12-15 12:47:14', 1, '2021-01-02 00:10:50', 1, 0);
INSERT INTO `tb_biz_entity` VALUES (3, 'Dictionary', 'Rac字典实体', 1, '', '2020-12-15 12:47:54', 1, '2021-01-02 00:10:47', 1, 0);
INSERT INTO `tb_biz_entity` VALUES (4, 'Dimension', 'RAC维度实体', 1, '', '2020-12-23 08:25:36', 1, '2021-01-02 00:10:44', 1, 0);
INSERT INTO `tb_biz_entity` VALUES (5, 'Dictionary-Org', '机构字典实体', 2, '', '2021-01-04 00:07:07', 1, '2021-01-04 14:15:22', 1, 0);
INSERT INTO `tb_biz_entity` VALUES (6, 'User', '用户实体', 2, '', '2021-01-05 01:30:04', 2, '2021-01-05 01:30:04', 2, 0);
COMMIT;

-- ----------------------------
-- Table structure for tb_biz_entity_control
-- ----------------------------
DROP TABLE IF EXISTS `tb_biz_entity_control`;
CREATE TABLE `tb_biz_entity_control` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `subject_entity_id` bigint(20) NOT NULL COMMENT '主体ID',
  `object_entity_id` bigint(20) NOT NULL COMMENT '客体ID',
  `biz_line_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '业务线ID',
  `description` varchar(1000) NOT NULL DEFAULT '' COMMENT '管控关系描述',
  `parent_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '父节点ID 默认-1',
  `parent_path` varchar(1000) NOT NULL DEFAULT '-1' COMMENT '父节点路径 默认-1',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人ID',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `update_user_id` bigint(20) NOT NULL COMMENT '更新人ID',
  `deleted` int(2) NOT NULL DEFAULT '0' COMMENT '0 未删除 1 已删除',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`) USING BTREE COMMENT '父节点索引',
  KEY `idx_parent_path` (`parent_path`) USING BTREE COMMENT '路径索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务实体表';

-- ----------------------------
-- Records of tb_biz_entity_control
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tb_biz_line
-- ----------------------------
DROP TABLE IF EXISTS `tb_biz_line`;
CREATE TABLE `tb_biz_line` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `en_name` varchar(50) NOT NULL DEFAULT '' COMMENT '英文名',
  `cn_name` varchar(50) NOT NULL DEFAULT '' COMMENT '中文名',
  `decentralized_control` int(2) NOT NULL DEFAULT '0' COMMENT '是否分级管控 0 集中管控 1 分级管控',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人ID',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `update_user_id` bigint(20) NOT NULL COMMENT '更新人ID',
  `deleted` int(2) NOT NULL DEFAULT '0' COMMENT '0 未删除 1 已删除',
  `decentralized_control_en_name` varchar(50) NOT NULL DEFAULT '' COMMENT '分级管控维度英文名',
  PRIMARY KEY (`id`),
  KEY `idx_en_name` (`en_name`) USING BTREE COMMENT '英文名索引'
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='业务线表';

-- ----------------------------
-- Records of tb_biz_line
-- ----------------------------
BEGIN;
INSERT INTO `tb_biz_line` VALUES (1, 'rac', 'RAC权限管理', 0, '', '2020-12-15 20:48:37', 1, '2020-12-23 21:09:51', 1, 0, '');
INSERT INTO `tb_biz_line` VALUES (2, 'sale', '直销系统', 0, '', '2021-01-03 22:04:03', 1, '2021-01-03 22:04:03', 1, 0, 'Org');
INSERT INTO `tb_biz_line` VALUES (3, '1', '12', 0, '1', '2021-01-06 22:02:17', 1, '2021-01-06 22:02:26', 1, 1, '1');
COMMIT;

-- ----------------------------
-- Table structure for tb_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `tb_dictionary`;
CREATE TABLE `tb_dictionary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `en_name` varchar(50) NOT NULL COMMENT '字典英文名',
  `cn_name` varchar(50) NOT NULL COMMENT '字典中文名',
  `biz_line_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '业务线ID',
  `use_ext_property` int(2) NOT NULL DEFAULT '1' COMMENT '是否需要扩展属性',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人ID',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `update_user_id` bigint(20) NOT NULL COMMENT '更新人ID',
  `deleted` int(2) NOT NULL DEFAULT '0' COMMENT '0 未删除 1 已删除',
  PRIMARY KEY (`id`),
  KEY `idx_en_name` (`biz_line_id`,`en_name`) USING BTREE COMMENT '英文名索引'
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Records of tb_dictionary
-- ----------------------------
BEGIN;
INSERT INTO `tb_dictionary` VALUES (1, 'OrgType', '机构类型', 2, 1, '', '2021-01-03 23:59:56', 1, '2021-01-04 14:14:59', 1, 0);
INSERT INTO `tb_dictionary` VALUES (2, 'Org', '机构', 2, 0, '', '2021-01-04 00:03:30', 1, '2021-01-04 14:15:01', 1, 0);
INSERT INTO `tb_dictionary` VALUES (3, 'PosType', '岗位类型', 2, 1, '', '2021-01-04 00:50:49', 1, '2021-01-04 00:50:49', 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for tb_dictionary_node
-- ----------------------------
DROP TABLE IF EXISTS `tb_dictionary_node`;
CREATE TABLE `tb_dictionary_node` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `en_name` varchar(50) NOT NULL COMMENT '英文名',
  `cn_name` varchar(50) NOT NULL COMMENT '中文名',
  `value` varchar(255) NOT NULL COMMENT '值',
  `biz_line_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '业务线ID',
  `dictionary_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '字典ID',
  `parent_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '父节点ID 默认-1',
  `parent_path` varchar(1000) NOT NULL DEFAULT '' COMMENT '父节点路径 默认-1',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人ID',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `update_user_id` bigint(20) NOT NULL COMMENT '更新人ID',
  `deleted` int(2) NOT NULL DEFAULT '0' COMMENT '0 未删除 1 已删除',
  PRIMARY KEY (`id`),
  KEY `idx_en_name` (`biz_line_id`,`dictionary_id`,`en_name`) USING BTREE COMMENT '英文名索引',
  KEY `idx_parent_id` (`parent_id`) USING BTREE COMMENT '父节点索引',
  KEY `idx_parent_path` (`parent_path`) USING BTREE COMMENT '路径索引'
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='维度节点类型';

-- ----------------------------
-- Records of tb_dictionary_node
-- ----------------------------
BEGIN;
INSERT INTO `tb_dictionary_node` VALUES (1, 'Headquarters', '总部', '1', 2, 1, -1, '/-1', '', '2021-01-04 00:01:53', 1, '2021-01-04 14:15:40', 1, 0);
INSERT INTO `tb_dictionary_node` VALUES (2, 'BranchOffice', '分公司', '2', 2, 1, -1, '/-1', '', '2021-01-04 00:02:26', 1, '2021-01-04 14:15:43', 1, 0);
INSERT INTO `tb_dictionary_node` VALUES (3, 'Agent', '代理商', '3', 2, 1, -1, '/-1', '', '2021-01-04 00:03:06', 1, '2021-01-07 03:59:16', 2, 0);
INSERT INTO `tb_dictionary_node` VALUES (4, 'bjzb', '北京总部', '1', 2, 2, -1, '/-1', '', '2021-01-04 00:04:24', 1, '2021-01-04 14:15:44', 1, 0);
INSERT INTO `tb_dictionary_node` VALUES (5, 'shfgs', '上海分公司', '2', 2, 2, -1, '/-1', '', '2021-01-04 00:04:55', 1, '2021-01-04 14:15:45', 1, 0);
INSERT INTO `tb_dictionary_node` VALUES (6, 'whfy', '武汉浮游', '3', 2, 2, -1, '/-1', '', '2021-01-04 00:06:28', 1, '2021-01-04 14:15:46', 1, 0);
INSERT INTO `tb_dictionary_node` VALUES (7, 'yxxs', '一线销售', '1', 2, 3, -1, '/-1', '', '2021-01-04 00:51:17', 1, '2021-01-04 00:51:17', 1, 0);
INSERT INTO `tb_dictionary_node` VALUES (8, 'xsjl', '销售经理', '2', 2, 3, -1, '/-1', '', '2021-01-04 00:51:31', 1, '2021-01-04 00:51:31', 1, 0);
INSERT INTO `tb_dictionary_node` VALUES (9, 'xszj', '销售总监', '3', 2, 3, -1, '/-1', '', '2021-01-04 00:51:43', 1, '2021-01-04 00:51:43', 1, 0);
INSERT INTO `tb_dictionary_node` VALUES (10, '1', '222', '3', 2, 2, 4, '/-1/4', '', '2021-01-04 06:43:22', 1, '2021-01-04 06:43:22', 1, 0);
INSERT INTO `tb_dictionary_node` VALUES (11, 'fdd', 'ddd', '3', 2, 1, 3, '/-1/3', '', '2021-05-16 07:58:36', 1, '2021-05-16 07:58:36', 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for tb_dimension
-- ----------------------------
DROP TABLE IF EXISTS `tb_dimension`;
CREATE TABLE `tb_dimension` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `en_name` varchar(50) NOT NULL COMMENT '维度英文名',
  `cn_name` varchar(50) NOT NULL COMMENT '维度中文名',
  `biz_line_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '业务线ID',
  `node_type_id` bigint(20) NOT NULL COMMENT '维度节点类型字典ID',
  `use_ext_property` int(2) NOT NULL DEFAULT '1' COMMENT '是否使用扩展属性 0 使用 1 不使用',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人ID',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `update_user_id` bigint(20) NOT NULL COMMENT '更新人ID',
  `deleted` int(2) NOT NULL DEFAULT '0' COMMENT '0 未删除 1 已删除',
  PRIMARY KEY (`id`),
  KEY `idx_en_name` (`biz_line_id`,`en_name`) USING BTREE COMMENT '英文名索引'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='维度';

-- ----------------------------
-- Records of tb_dimension
-- ----------------------------
BEGIN;
INSERT INTO `tb_dimension` VALUES (1, 'Pos', '直销岗位树', 2, 3, 1, '', '2021-01-04 01:04:55', 1, '2021-01-04 01:04:55', 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for tb_dimension_node
-- ----------------------------
DROP TABLE IF EXISTS `tb_dimension_node`;
CREATE TABLE `tb_dimension_node` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `en_name` varchar(50) NOT NULL COMMENT '维度英文名',
  `cn_name` varchar(50) NOT NULL COMMENT '维度中文名',
  `type` int(20) NOT NULL DEFAULT '-1' COMMENT '维度节点类型ID',
  `biz_line_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '业务线ID',
  `decentralized_control_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '分级管控ID',
  `dimension_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '维度ID',
  `parent_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '父节点ID 默认-1',
  `parent_path` varchar(1000) NOT NULL DEFAULT '' COMMENT '父节点路径 默认-1',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '0 生效 1 失效',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人ID',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `update_user_id` bigint(20) NOT NULL COMMENT '更新人ID',
  `deleted` int(2) NOT NULL DEFAULT '0' COMMENT '0 未删除 1 已删除',
  PRIMARY KEY (`id`),
  KEY `idx_en_name` (`biz_line_id`,`dimension_id`,`en_name`) USING BTREE COMMENT '英文名索引',
  KEY `idx_parent_id` (`parent_id`) USING BTREE COMMENT '父节点ID索引',
  KEY `idx_parent_path` (`parent_path`) USING BTREE COMMENT '父路径索引'
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='维度节点';

-- ----------------------------
-- Records of tb_dimension_node
-- ----------------------------
BEGIN;
INSERT INTO `tb_dimension_node` VALUES (1, 'sale1', '直销1岗', 7, 2, 5, 1, -1, '/-1', 0, '', '2021-01-04 09:50:21', 3, '2021-01-04 20:40:23', 3, 0);
INSERT INTO `tb_dimension_node` VALUES (2, '1', '1', 8, 2, 5, 1, 1, '/-1/1', 0, '', '2021-01-04 20:40:43', 3, '2021-01-04 22:04:09', 3, 1);
INSERT INTO `tb_dimension_node` VALUES (3, 'hr', 'hr岗', 8, 2, 5, 1, -1, '/-1', 0, '', '2021-01-04 20:41:20', 3, '2021-01-07 04:08:42', 3, 0);
INSERT INTO `tb_dimension_node` VALUES (4, 'bjfgs', '北京分公司', 9, 2, 4, 1, -1, '/-1', 0, '', '2021-05-16 09:00:17', 1, '2021-05-16 09:00:17', 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for tb_dimension_node_control
-- ----------------------------
DROP TABLE IF EXISTS `tb_dimension_node_control`;
CREATE TABLE `tb_dimension_node_control` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `subject_node_id` bigint(20) NOT NULL COMMENT '管控维度节点ID',
  `object_node_id` bigint(20) NOT NULL COMMENT '被管控维度节点ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_subject_node_id` (`subject_node_id`) USING BTREE COMMENT '主体节点ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='维度节点管控关系表';

-- ----------------------------
-- Records of tb_dimension_node_control
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tb_ext_data
-- ----------------------------
DROP TABLE IF EXISTS `tb_ext_data`;
CREATE TABLE `tb_ext_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ext_property_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '属性定义信息ID',
  `biz_data_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '业务数据ID',
  `biz_line_id` bigint(20) NOT NULL COMMENT '用户所属业务线ID',
  `value` varchar(1000) NOT NULL COMMENT '扩展属性值',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人ID',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `update_user_id` bigint(20) NOT NULL COMMENT '更新人ID',
  `deleted` int(2) NOT NULL DEFAULT '0' COMMENT '0 未删除 1 已删除',
  PRIMARY KEY (`id`),
  KEY `idx_property_data` (`biz_line_id`,`ext_property_id`,`biz_data_id`) USING BTREE COMMENT '业务数据属性索引'
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='扩展属性表';

-- ----------------------------
-- Records of tb_ext_data
-- ----------------------------
BEGIN;
INSERT INTO `tb_ext_data` VALUES (1, 1, 2, 1, '2', '', '2021-01-03 22:03:43', 1, '2021-01-04 14:16:40', 1, 0);
INSERT INTO `tb_ext_data` VALUES (2, 2, 4, 2, '1', '', '2021-01-04 00:11:42', 1, '2021-01-04 14:16:12', 1, 0);
INSERT INTO `tb_ext_data` VALUES (3, 2, 5, 2, '2', '', '2021-01-04 00:11:49', 1, '2021-01-04 14:16:13', 1, 0);
INSERT INTO `tb_ext_data` VALUES (5, 1, 3, 1, '2', '', '2021-01-04 00:39:47', 1, '2021-01-04 00:39:47', 1, 0);
INSERT INTO `tb_ext_data` VALUES (8, 3, 3, 1, '5,7,8,9', '', '2021-01-04 09:49:40', 1, '2021-01-04 09:49:40', 1, 0);
INSERT INTO `tb_ext_data` VALUES (10, 2, 6, 2, '3', '', '2021-01-07 03:59:27', 2, '2021-01-07 03:59:27', 2, 0);
COMMIT;

-- ----------------------------
-- Table structure for tb_ext_property
-- ----------------------------
DROP TABLE IF EXISTS `tb_ext_property`;
CREATE TABLE `tb_ext_property` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `en_name` varchar(50) NOT NULL DEFAULT '' COMMENT '英文名',
  `cn_name` varchar(50) NOT NULL DEFAULT '' COMMENT '中文名',
  `biz_entity_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '业务实体ID',
  `biz_line_id` bigint(20) NOT NULL COMMENT '用户所属业务线ID',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '字段类型  0 字符串 1 枚举值',
  `dictionary_id` bigint(20) DEFAULT NULL COMMENT '枚举值对应的字典ID',
  `default_value` varchar(50) DEFAULT '' COMMENT '字段默认值',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人ID',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `update_user_id` bigint(20) NOT NULL COMMENT '更新人ID',
  `deleted` int(2) NOT NULL DEFAULT '0' COMMENT '0 未删除 1 已删除',
  PRIMARY KEY (`id`),
  KEY `idx_en_name` (`biz_line_id`,`biz_entity_id`,`en_name`) USING BTREE COMMENT '英文名索引'
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='扩展属性表';

-- ----------------------------
-- Records of tb_ext_property
-- ----------------------------
BEGIN;
INSERT INTO `tb_ext_property` VALUES (1, 'authBizLineId', '授权业务线ID', 1, 1, 0, NULL, '', '', '2021-01-03 22:02:43', 1, '2021-01-04 14:09:50', 1, 0);
INSERT INTO `tb_ext_property` VALUES (2, 'orgType', '机构类型', 5, 2, 1, 1, '', '', '2021-01-04 00:09:30', 1, '2021-01-04 14:16:52', 1, 0);
INSERT INTO `tb_ext_property` VALUES (3, 'authDecentralizedControlId', '授权分级管控ID', 1, 1, 0, NULL, '', '', '2021-01-04 00:41:00', 1, '2021-01-04 15:25:26', 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for tb_func
-- ----------------------------
DROP TABLE IF EXISTS `tb_func`;
CREATE TABLE `tb_func` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `en_name` varchar(50) NOT NULL COMMENT '英文名',
  `cn_name` varchar(50) NOT NULL COMMENT '中文名',
  `biz_line_id` bigint(20) NOT NULL COMMENT '业务线ID',
  `content` varchar(4000) NOT NULL DEFAULT '' COMMENT '资源内容',
  `parent_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '父节点ID 默认-1',
  `parent_path` varchar(1000) NOT NULL DEFAULT '' COMMENT '父节点路径 默认-1',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人ID',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL COMMENT '更新人ID',
  `deleted` int(2) NOT NULL DEFAULT '0' COMMENT '0 未删除 1 已删除',
  PRIMARY KEY (`id`),
  KEY `idx_en_name` (`biz_line_id`,`en_name`) USING BTREE COMMENT '英文名索引',
  KEY `idx_parent_id` (`parent_id`) USING BTREE COMMENT '父节点ID索引',
  KEY `idx_parent_path` (`parent_path`) USING BTREE COMMENT '父路径索引'
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='资源表';

-- ----------------------------
-- Records of tb_func
-- ----------------------------
BEGIN;
INSERT INTO `tb_func` VALUES (1, 'bizline', '业务线管理', 1, '/rac/bizline/listPage;/rac/bizline/create;/rac/bizline/edit;/rac/bizline/delete', -1, '/-1', '', '2021-01-07 14:28:17', 1, '2020-12-19 21:28:27', 1, 0);
INSERT INTO `tb_func` VALUES (2, 'entity', '实体管理', 1, '/rac/bizentity/**', -1, '/-1', '', '2021-01-07 14:33:34', 1, '2020-12-15 20:13:55', 1, 0);
INSERT INTO `tb_func` VALUES (3, 'dictionary', '字典管理', 1, '/rac/dictionary/node/addExtProperty;/rac/dictionary/node/dropExtProperty;/rac/dictionary/listPage;/rac/dictionary/create;/rac/dictionary/edit;/rac/dictionary/delete;/rac/dictionary/node/create;/rac/dictionary/node/edit;/rac/dictionary/node/delete', -1, '/-1', '', '2021-01-07 15:01:00', 1, '2021-01-07 01:01:00', 1, 0);
INSERT INTO `tb_func` VALUES (4, 'ext-property', '扩展属性管理', 1, '/rac/property/listPage;/rac/property/create;/rac/property/edit;/rac/property/delete;', -1, '/-1', '', '2021-01-07 14:50:57', 1, '2021-01-07 00:50:57', 1, 0);
INSERT INTO `tb_func` VALUES (5, 'func', '功能管理', 1, '/rac/func/**', -1, '/-1', '', '2021-01-07 14:34:29', 1, '2020-12-15 20:12:50', 1, 0);
INSERT INTO `tb_func` VALUES (6, 'strategy', '策略管理', 1, '/rac/strategy/**', -1, '/-1', '', '2021-01-07 14:34:43', 1, '2021-01-04 11:54:06', 1, 0);
INSERT INTO `tb_func` VALUES (7, 'menu', '菜单管理', 1, '/rac/menu/**', -1, '/-1', '', '2021-01-07 14:35:06', 1, '2021-01-04 11:54:46', 1, 0);
INSERT INTO `tb_func` VALUES (8, 'role', '角色管理', 1, '/rac/role/bindAuth;/rac/role/unbindAuth;/rac/role/create;/rac/role/edit;/rac/role/delete;/rac/role/listPage;/rac/role/listAuth;/rac/role/listBindMenuId', -1, '/-1', '', '2021-01-07 17:57:43', 1, '2021-01-07 03:57:44', 1, 0);
INSERT INTO `tb_func` VALUES (9, 'dimension', '维度管理', 1, '/rac/dimension/create;/rac/dimension/edit;/rac/dimension/delete;', -1, '/-1', '', '2021-01-07 14:27:36', 1, '2021-01-04 08:52:53', 1, 0);
INSERT INTO `tb_func` VALUES (10, 'user', '用户管理', 1, '/rac/user/**', -1, '/-1', '', '2021-01-07 14:35:22', 1, '2020-12-20 05:11:18', 1, 0);
INSERT INTO `tb_func` VALUES (11, 'bizline-query', '业务线查询', 1, '/rac/bizline/listAuthBizLine', 1, '/-1/1', '', '2021-01-07 14:44:30', 1, '2021-01-04 11:52:28', 1, 0);
INSERT INTO `tb_func` VALUES (12, 'dictionary-query', '字典查询', 1, '/rac/dictionary/listByBizLineId;/rac/dictionary/queryByEnName;', 3, '/-1/3', '', '2021-01-07 14:49:49', 1, '2021-01-07 00:49:50', 1, 0);
INSERT INTO `tb_func` VALUES (13, 'dictionary-node-query', '字典节点查询', 1, '/rac/dictionary/node/listByDictionaryId;/rac/dictionary/node/listByParentId;/rac/dictionary/node/listExtProperty', 3, '/-1/3', '', '2021-01-07 14:54:39', 1, '2021-01-07 00:54:40', 1, 0);
INSERT INTO `tb_func` VALUES (14, 'ext-property-query', '扩展属性查询', 1, '/rac/property/listByBizEntityEnName', 4, '/-1/4', '', '2021-01-07 14:51:11', 1, '2021-01-07 00:51:12', 1, 0);
INSERT INTO `tb_func` VALUES (15, 'role-query', '角色查询', 1, '/rac/role/listByBizLineId', 8, '/-1/8', '', '2021-01-07 14:49:35', 1, '2021-01-07 00:49:36', 1, 0);
INSERT INTO `tb_func` VALUES (16, 'dimension-query', '维度查询', 1, '/rac/dimension/listPage;/rac/dimension/listByBizLineId', 9, '/-1/9', '', '2021-01-07 14:46:07', 1, '2021-01-04 20:39:56', 1, 0);
INSERT INTO `tb_func` VALUES (17, 'dimension-node', '维度节点管理', 1, '/rac/dimension/node/**', 9, '/-1/9', '', '2021-01-07 14:46:17', 1, '2021-01-04 09:00:07', 1, 0);
INSERT INTO `tb_func` VALUES (20, '1', '1', 2, '1', -1, '/-1', '', '2021-01-07 03:59:56', 2, '2021-05-16 07:29:15', 2, 1);
INSERT INTO `tb_func` VALUES (21, 'cust', '客户管理', 2, '/cust/**', -1, '/-1', '', '2021-05-16 07:29:11', 2, '2021-05-16 07:29:11', 2, 0);
COMMIT;

-- ----------------------------
-- Table structure for tb_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu`;
CREATE TABLE `tb_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `en_name` varchar(50) NOT NULL COMMENT '菜单英文名',
  `cn_name` varchar(50) NOT NULL COMMENT '菜单中文名',
  `biz_line_id` bigint(20) NOT NULL COMMENT '业务线ID',
  `url` varchar(255) NOT NULL COMMENT 'url',
  `seq` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `parent_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '父节点ID',
  `parent_path` varchar(1000) DEFAULT '' COMMENT '路径',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人ID',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `update_user_id` bigint(20) NOT NULL COMMENT '更新人ID',
  `deleted` int(2) NOT NULL DEFAULT '0' COMMENT '0 未删除 1 已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_en_name` (`biz_line_id`,`en_name`) USING BTREE COMMENT '英文名索引',
  KEY `idx_parent_id` (`parent_id`) USING BTREE COMMENT '父节点ID索引',
  KEY `idx_parent_path` (`parent_path`) USING BTREE COMMENT '父路径索引'
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='菜单表';

-- ----------------------------
-- Records of tb_menu
-- ----------------------------
BEGIN;
INSERT INTO `tb_menu` VALUES (1, 'system', '系统管理', 1, '#', 0, -1, '/-1', '', '2020-12-23 08:12:37', 1, '2021-01-03 17:04:02', 1, 0);
INSERT INTO `tb_menu` VALUES (2, 'authorized', '权限管理', 1, '#', 1, -1, '/-1', '', '2020-12-25 07:20:55', 1, '2021-01-03 17:04:38', 1, 0);
INSERT INTO `tb_menu` VALUES (3, 'dimension', '维度管理', 1, '/dimension', 2, -1, '/-1', '', '2020-12-25 07:21:35', 1, '2021-01-03 17:04:40', 1, 0);
INSERT INTO `tb_menu` VALUES (4, 'user', '用户管理', 1, '/user', 3, -1, '/-1', '', '2020-12-25 07:22:19', 1, '2021-01-03 17:04:44', 1, 0);
INSERT INTO `tb_menu` VALUES (5, 'bizline', '业务线管理', 1, '/system/bizline', 0, 1, '/-1/1', '', '2020-12-25 07:22:54', 1, '2021-01-03 17:05:17', 1, 0);
INSERT INTO `tb_menu` VALUES (6, 'dictionary', '字典管理', 1, '/system/dictionary', 1, 1, '/-1/1', '', '2020-12-25 07:23:48', 1, '2021-01-03 17:05:19', 1, 0);
INSERT INTO `tb_menu` VALUES (7, 'property', '扩展属性管理', 1, '/system/property', 2, 1, '/-1/1', '', '2020-12-25 07:24:23', 1, '2021-01-03 17:05:29', 1, 0);
INSERT INTO `tb_menu` VALUES (8, 'bizentity', '实体管理', 1, '/system/bizentity', 3, 1, '/-1/1', '', '2020-12-25 07:24:48', 1, '2021-01-03 17:05:32', 1, 0);
INSERT INTO `tb_menu` VALUES (9, 'auth', '角色管理', 1, '/authorized/auth', 0, 2, '/-1/2', '', '2020-12-25 07:25:27', 1, '2021-01-03 17:05:35', 1, 0);
INSERT INTO `tb_menu` VALUES (10, 'func', '功能管理', 1, '/authorized/func', 1, 2, '/-1/2', '', '2020-12-25 07:25:51', 1, '2021-01-03 17:05:38', 1, 0);
INSERT INTO `tb_menu` VALUES (11, 'strategy', '策略管理', 1, '/authorized/strategy', 2, 2, '/-1/2', '', '2020-12-25 07:26:26', 1, '2021-01-03 17:05:40', 1, 0);
INSERT INTO `tb_menu` VALUES (12, 'menu', '菜单管理', 1, '/authorized/menu', 3, 2, '/-1/2', '', '2020-12-25 07:30:56', 1, '2021-01-03 17:05:43', 1, 0);
INSERT INTO `tb_menu` VALUES (13, '1', '1', 2, '1', 1, -1, '/-1', '', '2021-01-07 04:00:26', 2, '2021-01-07 04:00:26', 2, 0);
COMMIT;

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `en_name` varchar(50) NOT NULL DEFAULT '' COMMENT '英文名',
  `cn_name` varchar(50) NOT NULL DEFAULT '' COMMENT '中文名',
  `biz_line_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '业务线ID ',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人ID',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `update_user_id` bigint(20) NOT NULL COMMENT '更新人ID',
  `deleted` int(2) NOT NULL DEFAULT '0' COMMENT '0 未删除 1 已删除',
  PRIMARY KEY (`id`),
  KEY `idx_en_name` (`biz_line_id`,`en_name`) USING BTREE COMMENT '英文名索引'
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of tb_role
-- ----------------------------
BEGIN;
INSERT INTO `tb_role` VALUES (1, 'super-admin', '超级管理员', 1, '', '2020-12-15 20:07:20', 1, '2020-12-19 12:54:14', 1, 0);
INSERT INTO `tb_role` VALUES (2, 'bizline-admin', '业务线管理员', 1, '', '2020-12-15 20:07:50', 1, '2020-12-19 12:37:44', 1, 0);
INSERT INTO `tb_role` VALUES (3, 'org-admin', '普通管理员', 1, '', '2020-12-15 20:08:21', 1, '2021-01-04 08:34:53', 1, 0);
INSERT INTO `tb_role` VALUES (4, 'sale', '销售角色', 2, '', '2021-01-04 09:23:29', 1, '2021-01-04 09:23:29', 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for tb_role_func
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_func`;
CREATE TABLE `tb_role_func` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `func_id` bigint(20) NOT NULL COMMENT '功能ID',
  `strategy_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '角色对应的功能访问策略',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人ID',
  PRIMARY KEY (`id`),
  KEY `idx_role_func` (`role_id`,`func_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='角色资源关系表';

-- ----------------------------
-- Records of tb_role_func
-- ----------------------------
BEGIN;
INSERT INTO `tb_role_func` VALUES (1, 1, 1, 1, '2021-01-06 23:14:10', 1);
INSERT INTO `tb_role_func` VALUES (2, 1, 2, 1, '2021-01-07 14:42:42', 1);
INSERT INTO `tb_role_func` VALUES (3, 1, 3, 1, '2021-01-07 14:42:44', 1);
INSERT INTO `tb_role_func` VALUES (4, 1, 4, 1, '2021-01-07 14:42:46', 1);
INSERT INTO `tb_role_func` VALUES (5, 1, 5, 1, '2021-01-07 14:42:48', 1);
INSERT INTO `tb_role_func` VALUES (6, 1, 6, 1, '2021-01-07 14:42:51', 1);
INSERT INTO `tb_role_func` VALUES (7, 1, 7, 1, '2021-01-07 14:42:54', 1);
INSERT INTO `tb_role_func` VALUES (8, 1, 8, 1, '2021-01-07 14:42:57', 1);
INSERT INTO `tb_role_func` VALUES (9, 1, 9, 1, '2021-01-07 14:43:00', 1);
INSERT INTO `tb_role_func` VALUES (10, 1, 10, 1, '2021-01-07 14:43:04', 1);
INSERT INTO `tb_role_func` VALUES (11, 2, 11, 2, '2021-01-07 18:02:15', 1);
INSERT INTO `tb_role_func` VALUES (12, 2, 2, 3, '2021-01-07 18:02:20', 1);
INSERT INTO `tb_role_func` VALUES (13, 2, 3, 3, '2021-01-07 18:02:22', 1);
INSERT INTO `tb_role_func` VALUES (14, 2, 4, 3, '2021-01-07 18:02:25', 1);
INSERT INTO `tb_role_func` VALUES (15, 2, 5, 3, '2021-01-07 18:02:28', 1);
INSERT INTO `tb_role_func` VALUES (16, 2, 6, 3, '2021-01-07 18:02:30', 1);
INSERT INTO `tb_role_func` VALUES (17, 2, 7, 3, '2021-01-07 18:02:33', 1);
INSERT INTO `tb_role_func` VALUES (18, 2, 8, 3, '2021-01-07 18:02:36', 1);
INSERT INTO `tb_role_func` VALUES (19, 2, 9, 3, '2021-01-07 18:02:39', 1);
INSERT INTO `tb_role_func` VALUES (20, 2, 10, 3, '2021-01-07 18:02:42', 1);
INSERT INTO `tb_role_func` VALUES (21, 3, 11, 2, '2021-01-07 18:06:39', 1);
INSERT INTO `tb_role_func` VALUES (22, 3, 12, 2, '2021-01-07 18:06:53', 1);
INSERT INTO `tb_role_func` VALUES (23, 3, 13, 4, '2021-01-07 18:07:07', 1);
INSERT INTO `tb_role_func` VALUES (24, 3, 14, 2, '2021-01-07 18:07:19', 1);
INSERT INTO `tb_role_func` VALUES (25, 3, 15, 2, '2021-01-07 18:07:30', 1);
INSERT INTO `tb_role_func` VALUES (26, 3, 16, 2, '2021-01-07 18:07:40', 1);
INSERT INTO `tb_role_func` VALUES (27, 3, 17, 5, '2021-01-07 18:07:50', 1);
INSERT INTO `tb_role_func` VALUES (28, 3, 10, 5, '2021-01-07 18:08:04', 1);
INSERT INTO `tb_role_func` VALUES (29, 4, 21, 6, '2021-05-16 20:30:13', 2);
COMMIT;

-- ----------------------------
-- Table structure for tb_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_menu`;
CREATE TABLE `tb_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人ID',
  PRIMARY KEY (`id`),
  KEY `idx_role_menu` (`role_id`,`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='角色菜单关系表';

-- ----------------------------
-- Records of tb_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `tb_role_menu` VALUES (1, 1, 3, '2021-01-04 12:01:08', 1);
INSERT INTO `tb_role_menu` VALUES (2, 1, 4, '2021-01-04 12:01:09', 1);
INSERT INTO `tb_role_menu` VALUES (3, 1, 5, '2021-01-04 12:01:09', 1);
INSERT INTO `tb_role_menu` VALUES (4, 1, 6, '2021-01-04 12:01:10', 1);
INSERT INTO `tb_role_menu` VALUES (5, 1, 7, '2021-01-04 12:01:11', 1);
INSERT INTO `tb_role_menu` VALUES (6, 1, 8, '2021-01-04 12:01:11', 1);
INSERT INTO `tb_role_menu` VALUES (7, 1, 9, '2021-01-04 12:01:12', 1);
INSERT INTO `tb_role_menu` VALUES (8, 1, 10, '2021-01-04 12:01:13', 1);
INSERT INTO `tb_role_menu` VALUES (9, 1, 11, '2021-01-04 12:01:14', 1);
INSERT INTO `tb_role_menu` VALUES (10, 1, 12, '2021-01-04 12:01:17', 1);
INSERT INTO `tb_role_menu` VALUES (11, 2, 3, '2021-01-04 13:58:00', 1);
INSERT INTO `tb_role_menu` VALUES (12, 2, 4, '2021-01-04 13:58:00', 1);
INSERT INTO `tb_role_menu` VALUES (13, 2, 6, '2021-01-04 13:58:00', 1);
INSERT INTO `tb_role_menu` VALUES (14, 2, 7, '2021-01-04 13:58:00', 1);
INSERT INTO `tb_role_menu` VALUES (15, 2, 8, '2021-01-04 13:58:00', 1);
INSERT INTO `tb_role_menu` VALUES (16, 2, 9, '2021-01-04 13:58:00', 1);
INSERT INTO `tb_role_menu` VALUES (17, 2, 10, '2021-01-04 13:58:00', 1);
INSERT INTO `tb_role_menu` VALUES (18, 2, 11, '2021-01-04 13:58:00', 1);
INSERT INTO `tb_role_menu` VALUES (19, 2, 12, '2021-01-04 13:58:00', 1);
INSERT INTO `tb_role_menu` VALUES (20, 3, 3, '2021-01-04 14:30:03', 1);
INSERT INTO `tb_role_menu` VALUES (21, 3, 4, '2021-01-04 14:30:03', 1);
COMMIT;

-- ----------------------------
-- Table structure for tb_strategy
-- ----------------------------
DROP TABLE IF EXISTS `tb_strategy`;
CREATE TABLE `tb_strategy` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '策略ID',
  `biz_line_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '策略所属业务线ID',
  `en_name` varchar(50) NOT NULL COMMENT '该访问策略的名称\n',
  `cn_name` varchar(50) NOT NULL COMMENT '该访问策略的名称\n',
  `type` int(11) NOT NULL DEFAULT '-1' COMMENT '访问控制策略的模板ID',
  `expression` varchar(200) NOT NULL COMMENT '该访问策略的表达式',
  `priority` int(11) DEFAULT '0' COMMENT '策略优先级',
  `remark` varchar(200) NOT NULL DEFAULT '' COMMENT '该访问策略的描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人ID',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `update_user_id` bigint(20) NOT NULL COMMENT '更新人ID',
  `deleted` int(2) NOT NULL DEFAULT '0' COMMENT '0 未删除 1 已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_en_name` (`biz_line_id`,`en_name`) USING BTREE,
  KEY `idx_cn_name` (`biz_line_id`,`cn_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='访问控制策略';

-- ----------------------------
-- Records of tb_strategy
-- ----------------------------
BEGIN;
INSERT INTO `tb_strategy` VALUES (1, 1, 'All', '全部数据', 1, '#All[]', 0, '', '2020-12-21 16:22:59', 1, '2021-01-01 23:45:32', 1, 0);
INSERT INTO `tb_strategy` VALUES (2, 1, 'BizlineId', 'RAC业务线ID管控策略', 2, '#UserProperty[Ext,authBizLineId,All[Basic,id]]', 0, '', '2021-01-04 08:31:14', 1, '2021-01-07 14:38:52', 1, 0);
INSERT INTO `tb_strategy` VALUES (3, 1, 'Bizline', 'RAC业务线管控策略', 2, '#UserProperty[Ext,authBizLineId,All[Basic,bizLineId]]', 1, '', '2020-12-21 16:26:11', 1, '2021-01-07 14:38:50', 1, 0);
INSERT INTO `tb_strategy` VALUES (4, 1, 'DecentralizedId', 'RAC字典ID管控策略', 2, '#UserProperty[Ext,authDecentralizedControlId,All[Basic,id]]', 0, '', '2021-01-04 08:30:35', 1, '2021-01-05 01:35:31', 1, 0);
INSERT INTO `tb_strategy` VALUES (5, 1, 'Decentralized', 'RAC字典管控策略', 2, '#UserProperty[Ext,authDecentralizedControlId,All[Basic,decentralizedControlId]]', 2, '', '2021-01-01 09:19:22', 1, '2021-01-07 14:38:39', 1, 0);
INSERT INTO `tb_strategy` VALUES (6, 2, 'all', '全部数据', 1, '#All', 1, '', '2021-05-16 07:29:55', 2, '2021-05-16 07:29:55', 2, 0);
COMMIT;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `en_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户登录名',
  `cn_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(50) DEFAULT '' COMMENT '用户密码',
  `id_card_num` varchar(50) NOT NULL DEFAULT '' COMMENT '身份证',
  `mobile_phone` varchar(50) DEFAULT NULL COMMENT '手机',
  `email` varchar(50) NOT NULL DEFAULT '' COMMENT '邮箱',
  `gender` int(2) NOT NULL COMMENT '性别 0 男 1 女',
  `biz_line_id` bigint(20) NOT NULL COMMENT '用户所属业务线ID',
  `decentralized_control_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '分级管控授权ID',
  `status` int(2) NOT NULL COMMENT '用户状态 0 正常  1 禁用',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人ID',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `update_user_id` bigint(20) NOT NULL COMMENT '更新人ID',
  `deleted` int(2) NOT NULL DEFAULT '0' COMMENT '0 未删除 1 已删除',
  `slat` varchar(255) DEFAULT NULL COMMENT '密码加盐',
  PRIMARY KEY (`id`),
  KEY `idx_en_name` (`en_name`) USING BTREE COMMENT '英文名索引'
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of tb_user
-- ----------------------------
BEGIN;
INSERT INTO `tb_user` VALUES (1, 'qingchuan', '晴川', '123456', '110242199002120982', '18203567829', 'qingchuan@qq.com', 0, 1, -1, 0, '13', '2020-12-15 20:30:53', 1, '2020-12-19 02:45:28', 1, 0, NULL);
INSERT INTO `tb_user` VALUES (2, 'SAdmin', '直销超管', '123456', '', '13277282822', '11@qq.com', 0, 1, -1, 0, '', '2021-01-03 22:03:36', 1, '2021-01-04 12:07:14', 1, 0, NULL);
INSERT INTO `tb_user` VALUES (3, 'ZBAdmin', '普通管理员', '123456', '', '18201038277', 'zbadmin@qq.com', 0, 1, 4, 0, '', '2021-01-04 00:22:46', 1, '2021-05-16 08:58:20', 1, 0, NULL);
INSERT INTO `tb_user` VALUES (4, 'sale1', '直销1', '', '', '182938728822', 'zhixiao1@qq.com', 0, 2, 5, 0, '', '2021-01-04 07:24:49', 3, '2021-01-04 07:24:49', 3, 0, NULL);
COMMIT;

-- ----------------------------
-- Table structure for tb_user_dimension_node
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_dimension_node`;
CREATE TABLE `tb_user_dimension_node` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `dimension_node_id` bigint(20) NOT NULL COMMENT '维度节点ID',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人ID',
  PRIMARY KEY (`id`),
  KEY `idx_user_dimension_node` (`user_id`,`dimension_node_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

-- ----------------------------
-- Records of tb_user_dimension_node
-- ----------------------------
BEGIN;
INSERT INTO `tb_user_dimension_node` VALUES (5, 4, 1, '2021-01-05 13:50:10', 3);
COMMIT;

-- ----------------------------
-- Table structure for tb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人ID',
  PRIMARY KEY (`id`),
  KEY `idx_user_role` (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

-- ----------------------------
-- Records of tb_user_role
-- ----------------------------
BEGIN;
INSERT INTO `tb_user_role` VALUES (1, 1, 1, '2021-01-03 17:23:13', 1);
INSERT INTO `tb_user_role` VALUES (2, 2, 2, '2021-01-04 12:06:57', 1);
INSERT INTO `tb_user_role` VALUES (3, 3, 3, '2021-01-04 14:27:16', 1);
INSERT INTO `tb_user_role` VALUES (4, 4, 4, '2021-01-05 13:29:30', 3);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
