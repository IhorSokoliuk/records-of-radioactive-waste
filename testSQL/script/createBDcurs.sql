-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema oblik_rao
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `oblik_rao` ;

-- -----------------------------------------------------
-- Schema oblik_rao
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `oblik_rao` DEFAULT CHARACTER SET utf8 ;
USE `oblik_rao` ;

-- -----------------------------------------------------
-- Table `oblik_rao`.`КАТЕГОРІЯ`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `oblik_rao`.`КАТЕГОРІЯ` ;

CREATE TABLE IF NOT EXISTS `oblik_rao`.`КАТЕГОРІЯ` (
  `НОМЕР` INT NOT NULL AUTO_INCREMENT,
  `НАЗВА` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`НОМЕР`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `oblik_rao`.`ТРВ`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `oblik_rao`.`ТРВ` ;

CREATE TABLE IF NOT EXISTS `oblik_rao`.`ТРВ` (
  `НОМЕР` INT NOT NULL AUTO_INCREMENT,
  `КАТЕГОРІЯ` INT NOT NULL,
  `КІЛЬКІСТЬ` INT NOT NULL,
  `ЗАГАЛЬНА_АКТИВНІСТЬ` INT NOT NULL,
  `НУКЛІД` INT NOT NULL,
  `АКТИВНІСТЬ_НУКЛІДА` INT NOT NULL,
  `ДАТА_ВИГОТОВЛЕННЯ` DATE NOT NULL,
  `ПРИМІТКИ` TINYTEXT NOT NULL,
  PRIMARY KEY (`НОМЕР`),
  CONSTRAINT `ключ-трв-категорія`
    FOREIGN KEY (`КАТЕГОРІЯ`)
    REFERENCES `oblik_rao`.`КАТЕГОРІЯ` (`НОМЕР`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `індекс-трв-категорія` ON `oblik_rao`.`ТРВ` (`КАТЕГОРІЯ` ASC);


-- -----------------------------------------------------
-- Table `oblik_rao`.`РРВ`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `oblik_rao`.`РРВ` ;

CREATE TABLE IF NOT EXISTS `oblik_rao`.`РРВ` (
  `НОМЕР` INT NOT NULL AUTO_INCREMENT,
  `КАТЕГОРІЯ` INT NOT NULL,
  `КІЛЬКІСТЬ` INT NOT NULL,
  `ЗАГАЛЬНА_АКТИВНІСТЬ` INT NOT NULL,
  `НУКЛІД` INT NOT NULL,
  `АКТИВНІСТЬ_НУКЛІДА` INT NOT NULL,
  `ДАТА_ВИГОТОВЛЕННЯ` DATE NOT NULL,
  `ПРИМІТКИ` TINYTEXT NOT NULL,
  PRIMARY KEY (`НОМЕР`),
  CONSTRAINT `ключ-ррв-категорія`
    FOREIGN KEY (`КАТЕГОРІЯ`)
    REFERENCES `oblik_rao`.`КАТЕГОРІЯ` (`НОМЕР`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `індекс-ррв-категорія` ON `oblik_rao`.`РРВ` (`КАТЕГОРІЯ` ASC);


-- -----------------------------------------------------
-- Table `oblik_rao`.`БРВ`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `oblik_rao`.`БРВ` ;

CREATE TABLE IF NOT EXISTS `oblik_rao`.`БРВ` (
  `НОМЕР` INT NOT NULL AUTO_INCREMENT,
  `КАТЕГОРІЯ` INT NOT NULL,
  `КІЛЬКІСТЬ` INT NOT NULL,
  `ЗАГАЛЬНА_АКТИВНІСТЬ` INT NOT NULL,
  `НУКЛІД` INT NOT NULL,
  `АКТИВНІСТЬ_НУКЛІДА` INT NOT NULL,
  `ДАТА_ВИГОТОВЛЕННЯ` DATE NOT NULL,
  `ПРИМІТКИ` TINYTEXT NOT NULL,
  PRIMARY KEY (`НОМЕР`),
  CONSTRAINT `ключ-брв-категорія`
    FOREIGN KEY (`КАТЕГОРІЯ`)
    REFERENCES `oblik_rao`.`КАТЕГОРІЯ` (`НОМЕР`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `індекс-брв-категорія` ON `oblik_rao`.`БРВ` (`КАТЕГОРІЯ` ASC);


-- -----------------------------------------------------
-- Table `oblik_rao`.`ДІВ`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `oblik_rao`.`ДІВ` ;

CREATE TABLE IF NOT EXISTS `oblik_rao`.`ДІВ` (
  `НОМЕР` INT NOT NULL AUTO_INCREMENT,
  `КАТЕГОРІЯ` INT NOT NULL,
  `КІЛЬКІСТЬ` INT NOT NULL,
  `ЗАГАЛЬНА_АКТИВНІСТЬ` INT NOT NULL,
  `НУКЛІД` INT NOT NULL,
  `АКТИВНІСТЬ_НУКЛІДА` INT NOT NULL,
  `ДАТА_ВИГОТОВЛЕННЯ` DATE NOT NULL,
  `ПРИМІТКИ` TINYTEXT NOT NULL,
  PRIMARY KEY (`НОМЕР`),
  CONSTRAINT `дів-категорія`
    FOREIGN KEY (`КАТЕГОРІЯ`)
    REFERENCES `oblik_rao`.`КАТЕГОРІЯ` (`НОМЕР`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `індекс-дів-категорія` ON `oblik_rao`.`ДІВ` (`КАТЕГОРІЯ` ASC);


-- -----------------------------------------------------
-- Table `oblik_rao`.`РАДІОНУКЛІД`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `oblik_rao`.`РАДІОНУКЛІД` ;

CREATE TABLE IF NOT EXISTS `oblik_rao`.`РАДІОНУКЛІД` (
  `НОМЕР` INT NOT NULL,
  `НАЗВА` TINYTEXT NOT NULL,
  `СКОРОЧЕНО` VARCHAR(10) NOT NULL,
  `НОМЕР_ПОРЯДКУ` INT NOT NULL,
  `АКТИВНІСТЬ_НУКЛІДА` INT NOT NULL,
  PRIMARY KEY (`НОМЕР`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `oblik_rao`.`КАТЕГОРІЯ_ВІДХОДУ`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `oblik_rao`.`КАТЕГОРІЯ_ВІДХОДУ` ;

CREATE TABLE IF NOT EXISTS `oblik_rao`.`КАТЕГОРІЯ_ВІДХОДУ` (
  `НОМЕР` INT NOT NULL AUTO_INCREMENT,
  `НАЗВА` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`НОМЕР`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `oblik_rao`.`ЗАВОД`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `oblik_rao`.`ЗАВОД` ;

CREATE TABLE IF NOT EXISTS `oblik_rao`.`ЗАВОД` (
  `НОМЕР` INT NOT NULL AUTO_INCREMENT,
  `КАТЕГОРІЯ_ВІДХОДУ` INT NOT NULL,
  `НУКЛІД` INT NOT NULL,
  `КІЛЬКІСТЬ` INT NOT NULL,
  `ДАТА_ВИГОТОВЛЕННЯ` DATE NOT NULL,
  `ПРИМІТКИ` TINYTEXT NOT NULL,
  PRIMARY KEY (`НОМЕР`),
  CONSTRAINT `ключ-завод-нуклід`
    FOREIGN KEY (`НУКЛІД`)
    REFERENCES `oblik_rao`.`РАДІОНУКЛІД` (`НОМЕР`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `ключ-завод-категорія_відходу`
    FOREIGN KEY (`КАТЕГОРІЯ_ВІДХОДУ`)
    REFERENCES `oblik_rao`.`КАТЕГОРІЯ_ВІДХОДУ` (`НОМЕР`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `індекс-завод-категорія_відходу` ON `oblik_rao`.`ЗАВОД` (`КАТЕГОРІЯ_ВІДХОДУ` ASC);

CREATE INDEX `індекс-завод-нуклід` ON `oblik_rao`.`ЗАВОД` (`НУКЛІД` ASC);

USE `oblik_rao`;

DELIMITER $$

USE `oblik_rao`$$
DROP TRIGGER IF EXISTS `oblik_rao`.`ЗАВОТ_ПІСЛЯ_ВСТАВКИ` $$
USE `oblik_rao`$$
CREATE DEFINER = CURRENT_USER TRIGGER `oblik_rao`.`ЗАВОТ_ПІСЛЯ_ВСТАВКИ` AFTER INSERT ON `ЗАВОД` FOR EACH ROW


begin





SET @НОМЕР = NEW.`НОМЕР`;


SET @КІЛЬКІСТЬ = NEW.`КІЛЬКІСТЬ`;


SET @АКТИВНІСТЬ_НУКЛІДА = 1;


SELECT `АКТИВНІСТЬ_НУКЛІДА` INTO @АКТИВНІСТЬ_НУКЛІДА FROM `РАДІОНУКЛІД` WHERE (`СКОРОЧЕНО`=NEW.`НУКЛІД`);


SET @ЗАГАЛЬНА_АКТИВНІСТЬ = @КІЛЬКІСТЬ * @АКТИВНІСТЬ_НУКЛІДА;


set @НУКЛІД = NEW.`НУКЛІД`;


set @КАТЕГОРІЯ = '3';


IF(@ЗАГАЛЬНА_АКТИВНІСТЬ < 1000000)then set @КАТЕГОРІЯ = '2'; END IF;


IF(@ЗАГАЛЬНА_АКТИВНІСТЬ < 10000) then set @КАТЕГОРІЯ = '1'; END IF;


set @ПРИМІТКИ = NEW.`ПРИМІТКИ`;


set @ДАТА_ВИГОТОВЛЕННЯ = NEW.`ДАТА_ВИГОТОВЛЕННЯ`;





IF(NEW.КАТЕГОРІЯ_ВІДХОДУ = "ТРВ")


then 


INSERT INTO `oblik_rao`.`ТРВ` (`НОМЕР`, `КАТЕГОРІЯ`, `КІЛЬКІСТЬ`, `ЗАГАЛЬНА_АКТИВНІСТЬ`, `НУКЛІД`, `АКТИВНІСТЬ_НУКЛІДА`, `ДАТА_ВИГОТОВЛЕННЯ`, `PRIMITKI`) VALUES (@НОМЕР, @КАТЕГОРІЯ, @КІЛЬКІСТЬ, @ЗАГАЛЬНА_АКТИВНІСТЬ, @НУКЛІД, @АКТИВНІСТЬ_НУКЛІДА, @ДАТА_ВИГОТОВЛЕННЯ, @ПРИМІТКИ);


END IF;





IF(NEW.КАТЕГОРІЯ_ВІДХОДУ = "БРВ")


then 


INSERT INTO `oblik_rao`.`БРВ` (`НОМЕР`, `КАТЕГОРІЯ`, `КІЛЬКІСТЬ`, `ЗАГАЛЬНА_АКТИВНІСТЬ`, `НУКЛІД`, `АКТИВНІСТЬ_НУКЛІДА`, `ДАТА_ВИГОТОВЛЕННЯ`, `PRIMITKI`) VALUES (@НОМЕР, @КАТЕГОРІЯ, @КІЛЬКІСТЬ, @ЗАГАЛЬНА_АКТИВНІСТЬ, @НУКЛІД, @АКТИВНІСТЬ_НУКЛІДА, @ДАТА_ВИГОТОВЛЕННЯ, @ПРИМІТКИ);


END IF;





IF(NEW.КАТЕГОРІЯ_ВІДХОДУ = "РРВ")


then 


INSERT INTO `oblik_rao`.`РРВ` (`НОМЕР`, `КАТЕГОРІЯ`, `КІЛЬКІСТЬ`, `ЗАГАЛЬНА_АКТИВНІСТЬ`, `НУКЛІД`, `АКТИВНІСТЬ_НУКЛІДА`, `ДАТА_ВИГОТОВЛЕННЯ`, `PRIMITKI`) VALUES (@НОМЕР, @КАТЕГОРІЯ, @КІЛЬКІСТЬ, @ЗАГАЛЬНА_АКТИВНІСТЬ, @НУКЛІД, @АКТИВНІСТЬ_НУКЛІДА, @ДАТА_ВИГОТОВЛЕННЯ, @ПРИМІТКИ);


END IF;





IF(NEW.КАТЕГОРІЯ_ВІДХОДУ = "ДІВ")


then 


INSERT INTO `oblik_rao`.`ДІВ` (`НОМЕР`, `КАТЕГОРІЯ`, `КІЛЬКІСТЬ`, `ЗАГАЛЬНА_АКТИВНІСТЬ`, `НУКЛІД`, `АКТИВНІСТЬ_НУКЛІДА`, `ДАТА_ВИГОТОВЛЕННЯ`, `PRIMITKI`) VALUES (@НОМЕР, @КАТЕГОРІЯ, @КІЛЬКІСТЬ, @ЗАГАЛЬНА_АКТИВНІСТЬ, @НУКЛІД, @АКТИВНІСТЬ_НУКЛІДА, @ДАТА_ВИГОТОВЛЕННЯ, @ПРИМІТКИ);


END IF;





end;$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `oblik_rao`.`КАТЕГОРІЯ`
-- -----------------------------------------------------
START TRANSACTION;
USE `oblik_rao`;
INSERT INTO `oblik_rao`.`КАТЕГОРІЯ` (`НОМЕР`, `НАЗВА`) VALUES (1, 'Слабоактивний');
INSERT INTO `oblik_rao`.`КАТЕГОРІЯ` (`НОМЕР`, `НАЗВА`) VALUES (2, 'Середньоактивний');
INSERT INTO `oblik_rao`.`КАТЕГОРІЯ` (`НОМЕР`, `НАЗВА`) VALUES (3, 'Сильноактивний');

COMMIT;


-- -----------------------------------------------------
-- Data for table `oblik_rao`.`КАТЕГОРІЯ_ВІДХОДУ`
-- -----------------------------------------------------
START TRANSACTION;
USE `oblik_rao`;
INSERT INTO `oblik_rao`.`КАТЕГОРІЯ_ВІДХОДУ` (`НОМЕР`, `НАЗВА`) VALUES (1, 'ТРВ');
INSERT INTO `oblik_rao`.`КАТЕГОРІЯ_ВІДХОДУ` (`НОМЕР`, `НАЗВА`) VALUES (2, 'БРВ');
INSERT INTO `oblik_rao`.`КАТЕГОРІЯ_ВІДХОДУ` (`НОМЕР`, `НАЗВА`) VALUES (3, 'РРВ');
INSERT INTO `oblik_rao`.`КАТЕГОРІЯ_ВІДХОДУ` (`НОМЕР`, `НАЗВА`) VALUES (4, 'ДІВ');

COMMIT;


-- -----------------------------------------------------
-- Data for table `oblik_rao`.`РАДІОНУКЛІД`
-- -----------------------------------------------------
START TRANSACTION;
USE `oblik_rao`;
INSERT INTO `oblik_rao`.`РАДІОНУКЛІД` (`НОМЕР`, `НАЗВА`, `СКОРОЧЕНО`, `НОМЕР_ПОРЯДКУ`, `АКТИВНІСТЬ_НУКЛІДА`) VALUES ('1', 'Криптон', 'Kr', '85', '33');
INSERT INTO `oblik_rao`.`РАДІОНУКЛІД` (`НОМЕР`, `НАЗВА`, `СКОРОЧЕНО`, `НОМЕР_ПОРЯДКУ`, `АКТИВНІСТЬ_НУКЛІДА`) VALUES ('2', 'Ксенон', 'Xe', '133', '6500');
INSERT INTO `oblik_rao`.`РАДІОНУКЛІД` (`НОМЕР`, `НАЗВА`, `СКОРОЧЕНО`, `НОМЕР_ПОРЯДКУ`, `АКТИВНІСТЬ_НУКЛІДА`) VALUES ('3', 'Теллур', 'Te', '132', '1150');
INSERT INTO `oblik_rao`.`РАДІОНУКЛІД` (`НОМЕР`, `НАЗВА`, `СКОРОЧЕНО`, `НОМЕР_ПОРЯДКУ`, `АКТИВНІСТЬ_НУКЛІДА`) VALUES ('4', 'Иод', 'I', '131', '1760');
INSERT INTO `oblik_rao`.`РАДІОНУКЛІД` (`НОМЕР`, `НАЗВА`, `СКОРОЧЕНО`, `НОМЕР_ПОРЯДКУ`, `АКТИВНІСТЬ_НУКЛІДА`) VALUES ('5', 'Цезий', 'Cs', '136', '36');
INSERT INTO `oblik_rao`.`РАДІОНУКЛІД` (`НОМЕР`, `НАЗВА`, `СКОРОЧЕНО`, `НОМЕР_ПОРЯДКУ`, `АКТИВНІСТЬ_НУКЛІДА`) VALUES ('6', 'Стронций', 'Sr', '89', '115');
INSERT INTO `oblik_rao`.`РАДІОНУКЛІД` (`НОМЕР`, `НАЗВА`, `СКОРОЧЕНО`, `НОМЕР_ПОРЯДКУ`, `АКТИВНІСТЬ_НУКЛІДА`) VALUES ('7', 'Рутений', 'Ru', '103', '168');
INSERT INTO `oblik_rao`.`РАДІОНУКЛІД` (`НОМЕР`, `НАЗВА`, `СКОРОЧЕНО`, `НОМЕР_ПОРЯДКУ`, `АКТИВНІСТЬ_НУКЛІДА`) VALUES ('8', 'Барий', 'Ba', '140', '240');
INSERT INTO `oblik_rao`.`РАДІОНУКЛІД` (`НОМЕР`, `НАЗВА`, `СКОРОЧЕНО`, `НОМЕР_ПОРЯДКУ`, `АКТИВНІСТЬ_НУКЛІДА`) VALUES ('9', 'Цирконий', 'Zr', '95', '84');
INSERT INTO `oblik_rao`.`РАДІОНУКЛІД` (`НОМЕР`, `НАЗВА`, `СКОРОЧЕНО`, `НОМЕР_ПОРЯДКУ`, `АКТИВНІСТЬ_НУКЛІДА`) VALUES ('10', 'Молибден', 'Mo', '99', '72');
INSERT INTO `oblik_rao`.`РАДІОНУКЛІД` (`НОМЕР`, `НАЗВА`, `СКОРОЧЕНО`, `НОМЕР_ПОРЯДКУ`, `АКТИВНІСТЬ_НУКЛІДА`) VALUES ('11', 'Церий', 'Ce', '141', '84');
INSERT INTO `oblik_rao`.`РАДІОНУКЛІД` (`НОМЕР`, `НАЗВА`, `СКОРОЧЕНО`, `НОМЕР_ПОРЯДКУ`, `АКТИВНІСТЬ_НУКЛІДА`) VALUES ('12', 'Нептуний', 'Np', '239', '400');
INSERT INTO `oblik_rao`.`РАДІОНУКЛІД` (`НОМЕР`, `НАЗВА`, `СКОРОЧЕНО`, `НОМЕР_ПОРЯДКУ`, `АКТИВНІСТЬ_НУКЛІДА`) VALUES ('13', 'Плутоний', 'Pu', '241', '3');
INSERT INTO `oblik_rao`.`РАДІОНУКЛІД` (`НОМЕР`, `НАЗВА`, `СКОРОЧЕНО`, `НОМЕР_ПОРЯДКУ`, `АКТИВНІСТЬ_НУКЛІДА`) VALUES ('14', 'Кюрий', 'Cm', '242', '1');

COMMIT;