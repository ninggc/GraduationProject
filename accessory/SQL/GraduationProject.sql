-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema gp
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema gp
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `gp` DEFAULT CHARACTER SET utf8 ;
USE `gp` ;

-- -----------------------------------------------------
-- Table `gp`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gp`.`user` (
  `account` VARCHAR(20) NOT NULL,
  `pass_word` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `addition` VARCHAR(45) NULL,
  PRIMARY KEY (`account`),
  UNIQUE INDEX `account_UNIQUE` (`account` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gp`.`process`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gp`.`process` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `peocess_name` VARCHAR(45) NOT NULL,
  `start_time` VARCHAR(45) NOT NULL DEFAULT 'now()',
  `description` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gp`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gp`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gp`.`stage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gp`.`stage` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `process_id` INT NOT NULL,
  `sequence` SMALLINT NOT NULL,
  `stage_name` VARCHAR(45) NULL,
  `pass` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `fk_stage_process1_idx` (`process_id` ASC),
  CONSTRAINT `fk_stage_process1`
    FOREIGN KEY (`process_id`)
    REFERENCES `gp`.`process` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gp`.`check_unit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gp`.`check_unit` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `stage_id` INT NOT NULL,
  `unit_name` VARCHAR(45) NOT NULL,
  `pass` TINYINT NOT NULL DEFAULT 0,
  `description` VARCHAR(45) NULL,
  INDEX `fk_check_unit_stage1_idx` (`stage_id` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_check_unit_stage1`
    FOREIGN KEY (`stage_id`)
    REFERENCES `gp`.`stage` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gp`.`file`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gp`.`file` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `process_id` INT NOT NULL,
  `filename` VARCHAR(45) NULL,
  `version` SMALLINT NULL,
  `md5` VARCHAR(45) NULL,
  INDEX `fk_file_process1_idx` (`process_id` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_file_process1`
    FOREIGN KEY (`process_id`)
    REFERENCES `gp`.`process` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gp`.`role_has_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gp`.`role_has_user` (
  `role_id` INT NOT NULL,
  `user_account` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`role_id`, `user_account`),
  INDEX `fk_role_has_user_user1_idx` (`user_account` ASC),
  INDEX `fk_role_has_user_role1_idx` (`role_id` ASC),
  CONSTRAINT `fk_role_has_user_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `gp`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_has_user_user1`
    FOREIGN KEY (`user_account`)
    REFERENCES `gp`.`user` (`account`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gp`.`role_has_check_unit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gp`.`role_has_check_unit` (
  `role_id` INT NOT NULL,
  `check_unit_id` INT NOT NULL,
  PRIMARY KEY (`role_id`, `check_unit_id`),
  INDEX `fk_role_has_check_unit_check_unit1_idx` (`check_unit_id` ASC),
  INDEX `fk_role_has_check_unit_role1_idx` (`role_id` ASC),
  CONSTRAINT `fk_role_has_check_unit_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `gp`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_has_check_unit_check_unit1`
    FOREIGN KEY (`check_unit_id`)
    REFERENCES `gp`.`check_unit` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
