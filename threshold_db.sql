CREATE TABLE `siem`.`thresholds` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `type` VARCHAR(255) NOT NULL,
  `number` INT NOT NULL,
  PRIMARY KEY (`id`));
insert into `siem`.`thresholds` (name, type, number) values ('test_count', 'test', 10);
SELECT * FROM `siem`.`thresholds`;