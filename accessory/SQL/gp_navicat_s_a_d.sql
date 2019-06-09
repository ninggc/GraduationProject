/*
Navicat MySQL Data Transfer

Source Server         : 119.28.72.123-ninggc
Source Server Version : 50725
Source Host           : 119.28.72.123:3306
Source Database       : gp

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-05-18 10:36:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for check_unit
-- ----------------------------
DROP TABLE IF EXISTS `check_unit`;
CREATE TABLE `check_unit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `process_id` int(11) NOT NULL,
  `stage_id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `pass` tinyint(4) NOT NULL DEFAULT '0',
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_check_unit_stage1_idx` (`stage_id`),
  CONSTRAINT `fk_check_unit_stage1` FOREIGN KEY (`stage_id`) REFERENCES `stage` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of check_unit
-- ----------------------------
INSERT INTO `check_unit` VALUES ('1', '1', '1', '1-1', '0', null);
INSERT INTO `check_unit` VALUES ('2', '1', '1', '1-2', '0', null);
INSERT INTO `check_unit` VALUES ('3', '1', '2', '2-1', '0', null);
INSERT INTO `check_unit` VALUES ('4', '1', '3', '3-1', '0', null);

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `process_id` int(11) NOT NULL,
  `account` varchar(255) DEFAULT NULL,
  `filename` varchar(45) DEFAULT NULL,
  `version` smallint(6) DEFAULT NULL,
  `md5` varchar(45) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_file_process1_idx` (`process_id`),
  CONSTRAINT `fk_file_process1` FOREIGN KEY (`process_id`) REFERENCES `process` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file
-- ----------------------------

-- ----------------------------
-- Table structure for process
-- ----------------------------
DROP TABLE IF EXISTS `process`;
CREATE TABLE `process` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `start_time` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of process
-- ----------------------------
INSERT INTO `process` VALUES ('1', '测试流程', '2015-1-1 19:00', 'MYSQL');
INSERT INTO `process` VALUES ('3', 'test', null, null);
INSERT INTO `process` VALUES ('4', 'test', null, null);
INSERT INTO `process` VALUES ('15', '', null, '');
INSERT INTO `process` VALUES ('16', '', null, '');
INSERT INTO `process` VALUES ('17', '', null, '');
INSERT INTO `process` VALUES ('18', '', null, '');

-- ----------------------------
-- Table structure for progress
-- ----------------------------
DROP TABLE IF EXISTS `progress`;
CREATE TABLE `progress` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(20) NOT NULL,
  `process_id` int(11) NOT NULL,
  `data` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `account` (`account`),
  KEY `process_id` (`process_id`),
  CONSTRAINT `progress_ibfk_1` FOREIGN KEY (`account`) REFERENCES `user` (`account`),
  CONSTRAINT `progress_ibfk_2` FOREIGN KEY (`process_id`) REFERENCES `process` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of progress
-- ----------------------------
INSERT INTO `progress` VALUES ('1', '1503130115', '1', '{\"1\":1,\"2\":0,\"3\":0,\"4\":0}');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '管理', '无');
INSERT INTO `role` VALUES ('7', 'manager', 'update');

-- ----------------------------
-- Table structure for role_has_check_unit
-- ----------------------------
DROP TABLE IF EXISTS `role_has_check_unit`;
CREATE TABLE `role_has_check_unit` (
  `unit_id` int(11) NOT NULL,
  `check_unit_id` int(11) NOT NULL,
  PRIMARY KEY (`unit_id`,`check_unit_id`),
  KEY `fk_role_has_check_unit_check_unit1_idx` (`check_unit_id`),
  KEY `fk_role_has_check_unit_role1_idx` (`unit_id`),
  CONSTRAINT `fk_role_has_check_unit_check_unit1` FOREIGN KEY (`check_unit_id`) REFERENCES `check_unit` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_has_check_unit_role1` FOREIGN KEY (`unit_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_has_check_unit
-- ----------------------------

-- ----------------------------
-- Table structure for role_has_user
-- ----------------------------
DROP TABLE IF EXISTS `role_has_user`;
CREATE TABLE `role_has_user` (
  `unit_id` int(11) NOT NULL,
  `user_account` varchar(20) NOT NULL,
  PRIMARY KEY (`unit_id`,`user_account`),
  KEY `fk_role_has_user_user1_idx` (`user_account`),
  KEY `fk_role_has_user_role1_idx` (`unit_id`),
  CONSTRAINT `fk_role_has_user_role1` FOREIGN KEY (`unit_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_has_user_user1` FOREIGN KEY (`user_account`) REFERENCES `user` (`account`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_has_user
-- ----------------------------

-- ----------------------------
-- Table structure for stage
-- ----------------------------
DROP TABLE IF EXISTS `stage`;
CREATE TABLE `stage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `process_id` int(11) NOT NULL,
  `sequence` int(6) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `pass` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_stage_process1_idx` (`process_id`),
  CONSTRAINT `fk_stage_process1` FOREIGN KEY (`process_id`) REFERENCES `process` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stage
-- ----------------------------
INSERT INTO `stage` VALUES ('1', '1', '1', '节点1', '1');
INSERT INTO `stage` VALUES ('2', '1', '2', '节点2', '0');
INSERT INTO `stage` VALUES ('3', '1', '3', '节点3', '0');
INSERT INTO `stage` VALUES ('6', '15', null, null, '0');
INSERT INTO `stage` VALUES ('7', '16', null, null, '0');
INSERT INTO `stage` VALUES ('8', '18', null, null, '0');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `account` varchar(20) NOT NULL,
  `pass_word` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `addition` varchar(45) DEFAULT NULL,
  `visible` tinyint(4) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`account`),
  UNIQUE KEY `account_UNIQUE` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('123', '45', '55', null, '0', '2019-05-17 20:57:22');
INSERT INTO `user` VALUES ('1503130115', '123', 'ning', 'student', '0', '2019-05-17 18:56:52');
INSERT INTO `user` VALUES ('1503130116', '123', '16', 'student', '0', '2019-05-17 18:56:52');
