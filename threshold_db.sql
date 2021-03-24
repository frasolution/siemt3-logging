CREATE TABLE `siem`.`thresholds` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `number` INT NOT NULL,
  PRIMARY KEY (`id`));
insert into `siem`.`thresholds` (name, number) values ('test_event_count', 10);
SELECT * FROM `siem`.`thresholds`;