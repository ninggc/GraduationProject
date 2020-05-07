-- auto Generated on 2019-04-19 15:47:33 
DROP TABLE IF EXISTS `process`;
CREATE TABLE process(
    `id` INTEGER(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `progress_id` INTEGER(20) UNSIGNED,
    `name` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'name',
    `start_time` VARCHAR(45) NOT NULL DEFAULT -1 COMMENT 'start_time',
    `description` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'description',
    `msg` VARCHAR(45),
    `files` VARCHAR(45),
    `visible` INTEGER(2) ,

    PRIMARY KEY (`id`),
    CONSTRAINT `progress_id` FOREIGN KEY (`progress_id`) REFERENCES `progress` (`id`)

)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'process';
