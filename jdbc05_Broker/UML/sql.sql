-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema broker
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema broker
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `broker` DEFAULT CHARACTER SET utf8 ;
USE `broker` ;

-- -----------------------------------------------------
-- Table `broker`.`customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `broker`.`customer` (
  `ssn` VARCHAR(20) NOT NULL,
  `cust_name` VARCHAR(20) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ssn`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `broker`.`stock`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `broker`.`stock` (
  `symbol` VARCHAR(20) NOT NULL,
  `price` DECIMAL(7,2) NOT NULL,
  PRIMARY KEY (`symbol`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `broker`.`shares`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `broker`.`shares` (
  `customer_ssn` VARCHAR(20) NOT NULL,
  `stock_symbol` VARCHAR(20) NOT NULL,
  `quantity` INT(2) NOT NULL,
  INDEX `fk_shares_customer_idx` (`customer_ssn` ASC) VISIBLE,
  INDEX `fk_shares_stock1_idx` (`stock_symbol` ASC) VISIBLE,
  CONSTRAINT `fk_shares_customer`
    FOREIGN KEY (`customer_ssn`)
    REFERENCES `broker`.`customer` (`ssn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_shares_stock1`
    FOREIGN KEY (`stock_symbol`)
    REFERENCES `broker`.`stock` (`symbol`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
