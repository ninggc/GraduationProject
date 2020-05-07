-- auto Generated on 2019-04-19 15:36:55
DROP TABLE IF EXISTS `role_has_user`;
CREATE TABLE role_has_user(
    `id` INTEGER(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'name',
    `description` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'description',
    `process_id` INTEGER(20),
    `scope` VARCHAR(45),
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'role_has_user';




