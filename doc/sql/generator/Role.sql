-- auto Generated on 2019-04-19 15:36:55 
-- DROP TABLE IF EXISTS `role`; 
CREATE TABLE role(
    `id` INTEGER(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'name',
    `description` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'description',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'role';




