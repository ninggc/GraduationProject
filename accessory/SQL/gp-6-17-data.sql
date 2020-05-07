/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50725
Source Host           : 127.0.0.1:3306
Source Database       : gp

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-06-17 12:49:27
*/

SET FOREIGN_KEY_CHECKS=0;


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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;


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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;


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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;


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
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

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
-- View structure for ap
-- ----------------------------
DROP VIEW IF EXISTS `ap`;
CREATE ALGORITHM=UNDEFINED DEFINER=`ninggc`@`%` SQL SECURITY DEFINER VIEW `ap` AS select `process`.`id` AS `id`,`process`.`name` AS `p_name`,`stage`.`process_id` AS `process_id`,`check_unit`.`stage_id` AS `stage_id`,`stage`.`sequence` AS `sequence`,`stage`.`name` AS `s_name`,`check_unit`.`name` AS `c_name` from ((`process` join `stage` on((`stage`.`process_id` = `process`.`id`))) join `check_unit` on((`check_unit`.`stage_id` = `stage`.`id`))) ;

-- ----------------------------
-- View structure for role with progress
-- ----------------------------
DROP VIEW IF EXISTS `role with progress`;
CREATE ALGORITHM=UNDEFINED DEFINER=`ninggc`@`%` SQL SECURITY DEFINER VIEW `role with progress` AS select `user`.`account` AS `account`,`role`.`name` AS `rolename`,`user`.`name` AS `username`,`check_unit`.`id` AS `c_id`,`check_unit`.`name` AS `unitname`,`check_unit`.`description` AS `description`,`process`.`name` AS `processname` from (((`role_has_user` join `role` on((`role_has_user`.`role_id` = `role`.`id`))) join `user` on((`role_has_user`.`user_account` = `user`.`account`))) join (`process` join `check_unit` on((`check_unit`.`process_id` = `process`.`id`)))) ;

-- ----------------------------
-- View structure for user_progress
-- ----------------------------
DROP VIEW IF EXISTS `user_progress`;
CREATE ALGORITHM=UNDEFINED DEFINER=`ninggc`@`%` SQL SECURITY DEFINER VIEW `user_progress` AS select `progress`.`account` AS `account`,`progress`.`process_id` AS `process_id`,`progress`.`data` AS `data`,`progress`.`current_sequence` AS `current_sequence`,`user`.`account` AS `u_account`,`user`.`name` AS `name` from (`progress` join `user` on((`progress`.`account` = `user`.`account`))) ;
