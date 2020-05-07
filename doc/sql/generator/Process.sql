-- auto Generated on 2019-04-19 15:47:33 
DROP TABLE IF EXISTS `process`;
CREATE TABLE process(
    `id` INTEGER(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'name',
    `start_time` BIGINT NOT NULL DEFAULT -1 COMMENT 'start_time',
    `description` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'description',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'process';
