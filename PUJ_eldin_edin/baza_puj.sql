-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema attendence_managment
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema attendence_managment
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `attendence_managment` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `attendence_managment` ;

-- -----------------------------------------------------
-- Table `attendence_managment`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `attendence_managment`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  `role` ENUM('Employee', 'Manager', 'SuperAdmin') NOT NULL,
  `image_url` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 55
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `attendence_managment`.`tasks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `attendence_managment`.`tasks` (
  `taskId` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(255) NOT NULL,
  `isCompleted` TINYINT(1) NULL DEFAULT '0',
  `employeeId` INT NULL DEFAULT NULL,
  PRIMARY KEY (`taskId`),
  INDEX `employeeId` (`employeeId` ASC) VISIBLE,
  CONSTRAINT `tasks_ibfk_1`
    FOREIGN KEY (`employeeId`)
    REFERENCES `attendence_managment`.`users` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
