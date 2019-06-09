/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50725
Source Host           : 127.0.0.1:3306
Source Database       : gp

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-06-09 20:17:03
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
  `role_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(45) NOT NULL,
  `pass` tinyint(4) NOT NULL DEFAULT '0',
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_check_unit_stage1_idx` (`stage_id`) USING BTREE,
  CONSTRAINT `check_unit_ibfk_1` FOREIGN KEY (`stage_id`) REFERENCES `stage` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of check_unit
-- ----------------------------
INSERT INTO `check_unit` VALUES ('1', '1', '1', '1', '1-1', '1', '属于stage1');
INSERT INTO `check_unit` VALUES ('2', '1', '1', '1', '1-2', '1', '属于stage1');
INSERT INTO `check_unit` VALUES ('3', '1', '2', '1', '2-1', '1', '属于stage2');
INSERT INTO `check_unit` VALUES ('4', '1', '3', '1', '3-1', '0', '属于stage2');
INSERT INTO `check_unit` VALUES ('5', '19', '4', '1', '单元1', '0', '属于节点1');
INSERT INTO `check_unit` VALUES ('6', '19', '5', '1', '单元2', '0', '属于节点2');

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
  KEY `fk_file_process1_idx` (`process_id`) USING BTREE,
  CONSTRAINT `file_ibfk_1` FOREIGN KEY (`process_id`) REFERENCES `process` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file
-- ----------------------------
INSERT INTO `file` VALUES ('7', '19', '1503130115', 'getitfree_20190422_ApowerREC License.pdf', null, null, 'C:\\Users\\NINGGC\\AppData\\Local\\Temp\\tomcat-docbase.9080330700363524341.8080\\upload\\1503130115\\19\\getitfree_20190422_ApowerREC License.pdf');
INSERT INTO `file` VALUES ('8', '19', '1503130115', 'getitfree_20190422_ApowerREC License.pdf', null, null, 'C:\\Users\\NINGGC\\AppData\\Local\\Temp\\tomcat-docbase.731590803959864998.8080\\upload\\1503130115\\19\\getitfree_20190422_ApowerREC License.pdf');
INSERT INTO `file` VALUES ('9', '19', '1503130115', 'getitfree_20190422_ApowerREC License.pdf', null, null, 'C:\\Users\\NINGGC\\AppData\\Local\\Temp\\tomcat-docbase.731590803959864998.8080\\upload\\1503130115\\19\\1503130115\\19\\getitfree_20190422_ApowerREC License.pdf');
INSERT INTO `file` VALUES ('10', '19', '1503130115', 'getitfree_20190422_ApowerREC License.pdf', null, null, 'C:\\Users\\NINGGC\\AppData\\Local\\Temp\\tomcat-docbase.2382130976279881274.8080\\upload\\1503130115\\19\\getitfree_20190422_ApowerREC License.pdf');
INSERT INTO `file` VALUES ('11', '19', '1503130115', 'getitfree_20190422_ApowerREC License.pdf', null, null, 'C:\\Users\\NINGGC\\AppData\\Local\\Temp\\tomcat-docbase.6412726939495439864.8080\\upload\\1503130115\\19\\getitfree_20190422_ApowerREC License.pdf');
INSERT INTO `file` VALUES ('12', '19', '1503130115', 'getitfree_20190422_ApowerREC License.pdf', null, null, 'C:\\Users\\NINGGC\\AppData\\Local\\Temp\\tomcat-docbase.6412726939495439864.8080\\upload\\1503130115\\19\\1503130115\\19\\getitfree_20190422_ApowerREC License.pdf');
INSERT INTO `file` VALUES ('13', '19', '1503130115', 'getitfree_20190422_ApowerREC License.pdf', null, null, 'C:\\Users\\NINGGC\\AppData\\Local\\Temp\\tomcat-docbase.1386472973439892508.8080\\upload\\1503130115\\19\\getitfree_20190422_ApowerREC License.pdf');
INSERT INTO `file` VALUES ('14', '19', '1503130115', 'getitfree_20190422_ApowerREC License.pdf', null, null, 'C:\\Users\\NINGGC\\AppData\\Local\\Temp\\tomcat-docbase.1386472973439892508.8080\\upload\\1503130115\\19\\1503130115\\19\\getitfree_20190422_ApowerREC License.pdf');
INSERT INTO `file` VALUES ('15', '19', '1503130115', 'getitfree_20190422_ApowerREC License.pdf', null, null, 'C:\\Users\\NINGGC\\AppData\\Local\\Temp\\tomcat-docbase.1386472973439892508.8080\\upload\\1503130115\\19\\1503130115\\19\\1503130115\\19\\getitfree_20190422_ApowerREC License.pdf');
INSERT INTO `file` VALUES ('16', '19', '1503130115', 'getitfree_20190422_ApowerREC License.pdf', null, null, 'C:\\Users\\NINGGC\\AppData\\Local\\Temp\\tomcat-docbase.3954386785637754463.8080\\upload\\1503130115\\19\\getitfree_20190422_ApowerREC License.pdf');
INSERT INTO `file` VALUES ('17', '19', '1503130115', 'getitfree_20190422_ApowerREC License.pdf', null, null, 'C:\\Users\\NINGGC\\AppData\\Local\\Temp\\tomcat-docbase.3954386785637754463.8080\\upload\\1503130115\\19\\1503130115\\19\\getitfree_20190422_ApowerREC License.pdf');
INSERT INTO `file` VALUES ('18', '19', '1503130115', 'getitfree_20190422_ApowerREC License.pdf', null, null, 'C:\\Users\\NINGGC\\AppData\\Local\\Temp\\tomcat-docbase.3954386785637754463.8080\\upload\\1503130115\\19\\1503130115\\19\\1503130115\\19\\getitfree_20190422_ApowerREC License.pdf');
INSERT INTO `file` VALUES ('19', '19', '1503130115', 'file1.pdf', null, null, 'C:\\Users\\NINGGC\\AppData\\Local\\Temp\\tomcat-docbase.3824037166081102864.8080\\upload\\1503130115\\19\\file1.pdf');
INSERT INTO `file` VALUES ('20', '19', '1503130115', 'file2.pdf', null, null, 'C:\\Users\\NINGGC\\AppData\\Local\\Temp\\tomcat-docbase.3824037166081102864.8080\\upload\\1503130115\\19\\1503130115\\19\\file2.pdf');
INSERT INTO `file` VALUES ('21', '19', '1503130115', 'file3.pdf', null, null, 'C:\\Users\\NINGGC\\AppData\\Local\\Temp\\tomcat-docbase.3824037166081102864.8080\\upload\\1503130115\\19\\1503130115\\19\\1503130115\\19\\file3.pdf');
INSERT INTO `file` VALUES ('22', '19', '1503130115', 'file1.pdf', null, null, 'C:\\Users\\NINGGC\\AppData\\Local\\Temp\\tomcat-docbase.347915106404268915.8080\\upload\\1503130115\\19\\file1.pdf');
INSERT INTO `file` VALUES ('23', '19', '1503130115', 'file2.pdf', null, null, 'C:\\Users\\NINGGC\\AppData\\Local\\Temp\\tomcat-docbase.347915106404268915.8080\\upload\\1503130115\\19\\1503130115\\19\\file2.pdf');
INSERT INTO `file` VALUES ('24', '19', '1503130115', 'file3.pdf', null, null, 'C:\\Users\\NINGGC\\AppData\\Local\\Temp\\tomcat-docbase.347915106404268915.8080\\upload\\1503130115\\19\\1503130115\\19\\1503130115\\19\\file3.pdf');
INSERT INTO `file` VALUES ('25', '1', '1503130115', 'file1.pdf', null, null, 'C:\\Users\\NINGGC\\AppData\\Local\\Temp\\tomcat-docbase.3980798155033825514.8080\\upload\\1503130115\\1\\file1.pdf');
INSERT INTO `file` VALUES ('26', '1', '1503130115', 'file1.pdf', null, null, 'C:\\Users\\NINGGC\\AppData\\Local\\Temp\\tomcat-docbase.3980798155033825514.8080\\upload\\1503130115\\1\\1503130115\\1\\file1.pdf');
INSERT INTO `file` VALUES ('27', '1', '1503130115', 'file1.pdf', null, null, 'C:\\Users\\NINGGC\\AppData\\Local\\Temp\\tomcat-docbase.3980798155033825514.8080\\upload\\1503130115\\1\\1503130115\\1\\1503130115\\1\\file1.pdf');
INSERT INTO `file` VALUES ('28', '1', '1503130115', 'file1.pdf', null, null, 'C:\\Users\\NINGGC\\AppData\\Local\\Temp\\tomcat-docbase.3980798155033825514.8080\\upload\\1503130115\\1\\1503130115\\1\\1503130115\\1\\1503130115\\1\\file1.pdf');
INSERT INTO `file` VALUES ('29', '19', '1503130115', 'file1.pdf', null, null, 'C:\\Users\\NINGGC\\AppData\\Local\\Temp\\tomcat-docbase.4209177839856488557.8080\\upload\\1503130115\\19\\file1.pdf');
INSERT INTO `file` VALUES ('30', '19', '1503130115', 'file2.pdf', null, null, 'C:\\Users\\NINGGC\\AppData\\Local\\Temp\\tomcat-docbase.4209177839856488557.8080\\upload\\1503130115\\19\\1503130115\\19\\file2.pdf');
INSERT INTO `file` VALUES ('31', '19', '1503130115', 'file3.pdf', null, null, 'C:\\Users\\NINGGC\\AppData\\Local\\Temp\\tomcat-docbase.4209177839856488557.8080\\upload\\1503130115\\19\\1503130115\\19\\1503130115\\19\\file3.pdf');

-- ----------------------------
-- Table structure for process
-- ----------------------------
DROP TABLE IF EXISTS `process`;
CREATE TABLE `process` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `start_time` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `msg` json DEFAULT NULL,
  `files` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of process
-- ----------------------------
INSERT INTO `process` VALUES ('1', '测试流程', '2015-1-1 19:00', 'MYSQL');
INSERT INTO `process` VALUES ('19', 'msg files', null, null);

-- ----------------------------
-- Table structure for progress
-- ----------------------------
DROP TABLE IF EXISTS `progress`;
CREATE TABLE `progress` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(20) NOT NULL,
  `process_id` int(11) NOT NULL,
  `data` varchar(255) DEFAULT NULL,
  `msg` varchar(255) DEFAULT NULL,
  `files` varchar(45) DEFAULT NULL,
  `current_sequence` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `account` (`account`) USING BTREE,
  KEY `process_id` (`process_id`) USING BTREE,
  CONSTRAINT `progress_ibfk_1` FOREIGN KEY (`account`) REFERENCES `user` (`account`),
  CONSTRAINT `progress_ibfk_2` FOREIGN KEY (`process_id`) REFERENCES `process` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of progress
-- ----------------------------
INSERT INTO `progress` VALUES ('29', '1503130115', '1', '{\"1\":{\"process_id\":1,\"stage_id\":1,\"unit_id\":1,\"pass\":1,\"description\":\"\"},\"2\":{\"process_id\":1,\"stage_id\":1,\"unit_id\":2,\"pass\":1,\"description\":\"\"},\"3\":{\"pass\":0,\"description\":\"无\"},\"4\":{\"pass\":0,\"description\":\"无\"}}', '{\"m1\":\"信息一\",\" m2\":\"信息二\"}', '{\"f1\":\"27\",\"f2\":\"28\"}', '2');
INSERT INTO `progress` VALUES ('31', '1503130115', '19', '{\"5\":{\"pass\":0,\"description\":\"无\"},\"6\":{\"pass\":0,\"description\":\"无\"}}', '{\"qq\":\"内容1\",\" ww\":\"内容2\",\" ee\":\"内容3\"}', '{\"aaa\":\"29\",\"sss\":\"30\",\"ddd\":\"31\"}', '1');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  `scope` json DEFAULT NULL,
  `process_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('0', '未绑定', null, null);
INSERT INTO `role` VALUES ('1', '管理', '无', null);
INSERT INTO `role` VALUES ('7', 'manager', 'update', null);
INSERT INTO `role` VALUES ('8', 'test role', 'This is test for \nADD', null);

-- ----------------------------
-- Table structure for role_has_check_unit
-- ----------------------------
DROP TABLE IF EXISTS `role_has_check_unit`;
CREATE TABLE `role_has_check_unit` (
  `role_id` int(11) NOT NULL,
  `check_unit_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`check_unit_id`),
  KEY `fk_role_has_check_unit_check_unit1_idx` (`check_unit_id`) USING BTREE,
  KEY `fk_role_has_check_unit_role1_idx` (`role_id`) USING BTREE,
  CONSTRAINT `role_has_check_unit_ibfk_1` FOREIGN KEY (`check_unit_id`) REFERENCES `check_unit` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `role_has_check_unit_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_has_check_unit
-- ----------------------------
INSERT INTO `role_has_check_unit` VALUES ('1', '1');

-- ----------------------------
-- Table structure for role_has_user
-- ----------------------------
DROP TABLE IF EXISTS `role_has_user`;
CREATE TABLE `role_has_user` (
  `role_id` int(11) NOT NULL,
  `user_account` varchar(20) NOT NULL,
  PRIMARY KEY (`role_id`,`user_account`),
  KEY `fk_role_has_user_user1_idx` (`user_account`) USING BTREE,
  KEY `fk_role_has_user_role1_idx` (`role_id`) USING BTREE,
  CONSTRAINT `role_has_user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `role_has_user_ibfk_2` FOREIGN KEY (`user_account`) REFERENCES `user` (`account`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_has_user
-- ----------------------------
INSERT INTO `role_has_user` VALUES ('1', '15031301');

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
  KEY `fk_stage_process1_idx` (`process_id`) USING BTREE,
  CONSTRAINT `stage_ibfk_1` FOREIGN KEY (`process_id`) REFERENCES `process` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stage
-- ----------------------------
INSERT INTO `stage` VALUES ('1', '1', '1', '节点1', '1');
INSERT INTO `stage` VALUES ('2', '1', '2', '节点2', '1');
INSERT INTO `stage` VALUES ('3', '1', '3', '节点3', '0');
INSERT INTO `stage` VALUES ('4', '19', '1', '阶段1', '0');
INSERT INTO `stage` VALUES ('5', '19', '2', '阶段2', '0');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `account` varchar(20) NOT NULL,
  `pass_word` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `addition` varchar(45) DEFAULT NULL,
  `visible` tinyint(4) NOT NULL DEFAULT '1',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`account`),
  UNIQUE KEY `account_UNIQUE` (`account`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('15031301', '123', 'teacher_1', 'teacher', '1', '2019-06-02 18:37:23');
INSERT INTO `user` VALUES ('1503130101', '123', 'invisible', 'student', '0', '2019-05-19 10:47:58');
INSERT INTO `user` VALUES ('1503130115', '123', 'ning', 'student', '1', '2019-05-17 18:56:52');
INSERT INTO `user` VALUES ('1503130116', '123', 'alex', 'student', '1', '2019-05-17 18:56:52');
