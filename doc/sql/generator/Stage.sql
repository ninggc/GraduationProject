-- auto Generated on 2019-04-19 15:47:33 
-- DROP TABLE IF EXISTS `stage`; 
CREATE TABLE stage(
    `id` INTEGER(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `process_id` INTEGER(12) NOT NULL DEFAULT -1 COMMENT 'process_id',
    `order` TINYINT NOT NULL DEFAULT -1 COMMENT 'order',
    `name` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'name',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'stage';
