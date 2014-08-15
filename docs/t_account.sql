/*
Navicat MySQL Data Transfer

Source Server         : 172.16.0.90:3306
Source Server Version : 50505
Source Host           : 172.16.0.90:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2014-08-15 17:29:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_account`
-- ----------------------------
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(30) DEFAULT NULL,
  `is_Admin` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_account
-- ----------------------------
INSERT INTO `t_account` VALUES ('1', 'aa', 'aa@qq.com', '20');
INSERT INTO `t_account` VALUES ('2', 'bb', 'bb@qq.com', '32');
INSERT INTO `t_account` VALUES ('3', 'cc', 'cc@qq.com', '23');
INSERT INTO `t_account` VALUES ('32', 'scala', 'bbbb', '0');
INSERT INTO `t_account` VALUES ('33', 'scala', 'bbbb', '0');
INSERT INTO `t_account` VALUES ('34', 'scala', 'bbbb', '0');
INSERT INTO `t_account` VALUES ('35', 'scala', 'bbbb', '0');
INSERT INTO `t_account` VALUES ('36', 'scala', 'bbbb', '0');
