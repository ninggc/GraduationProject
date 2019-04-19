-- auto Generated on 2019-04-19 15:47:33 
-- DROP TABLE IF EXISTS `check_unit`; 
CREATE TABLE check_unit(
    `id` INTEGER(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `stage_id` INTEGER(12) NOT NULL DEFAULT -1 COMMENT 'stage_id',
    `name` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'name',
    `description` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'description',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'check_unit';
