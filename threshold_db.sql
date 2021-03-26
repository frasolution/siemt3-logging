CREATE TABLE `siem`.`thresholds` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `type` VARCHAR(255) NOT NULL,
  `number` INT NOT NULL,
  PRIMARY KEY (`id`));
insert into `siem`.`thresholds` (name, type, number) values ('test_count', 'test', 10);
insert into `siem`.`thresholds` (name, type, number) values ('ssh_dictionary_basic_count', 'ssh', 5);
insert into `siem`.`thresholds` (name, type, number) values ('ssh_dictionary_basic_time', 'ssh', 5);
insert into `siem`.`thresholds` (name, type, number) values ('ssh_dictionary_elevated_count', 'ssh', 2);
insert into `siem`.`thresholds` (name, type, number) values ('ssh_dictionary_elevated_time', 'ssh', 10);
insert into `siem`.`thresholds` (name, type, number) values ('ssh_root_elevated_count', 'ssh', 2);
insert into `siem`.`thresholds` (name, type, number) values ('ssh_root_elevated_time', 'ssh', 1);
SELECT * FROM `siem`.`thresholds`;