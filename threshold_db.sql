CREATE TABLE `siem`.`thresholds` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `type` VARCHAR(255) NOT NULL,
  `number` INT NOT NULL,
  PRIMARY KEY (`id`));
SELECT * FROM `siem`.`thresholds`;