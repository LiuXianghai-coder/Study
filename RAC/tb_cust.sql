/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : customer

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 27/12/2020 17:38:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_cust
-- ----------------------------
DROP TABLE IF EXISTS `tb_cust`;
CREATE TABLE `tb_cust` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) NOT NULL DEFAULT '',
  `pos_id` bigint(20) DEFAULT NULL COMMENT '岗位ID ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cust
-- ----------------------------
BEGIN;
INSERT INTO `tb_cust` VALUES (1, '康乾唐鼎有限公司', 19);
INSERT INTO `tb_cust` VALUES (2, '窦豆丹服饰', 20);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
