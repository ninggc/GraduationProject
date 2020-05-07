-- auto Generated on 2019-04-19 15:48:36 
DROP TABLE IF EXISTS `user`;
CREATE TABLE user(
    `id` INTEGER(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `account` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'account',
    `pass_word` VARCHAR(45) NOT NULL DEFAULT '' COMMENT '计算后的md5',
    `name` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'name',
    `addition` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'addition',
    `visible` INTEGER(2) ,
    `update_time` DATETIME,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'user';
