-- auto Generated on 2019-05-05 15:26:05 
-- DROP TABLE IF EXISTS `progress`; 
CREATE TABLE progress(
    `id` INTEGER(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `account` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'account',
    `process_id` INTEGER(12) NOT NULL DEFAULT -1 COMMENT 'process_id',
    `data` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'data',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'progress';
