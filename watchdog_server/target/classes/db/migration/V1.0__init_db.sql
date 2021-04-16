CREATE SCHEMA IF NOT EXISTS `SIEM`;

CREATE TABLE IF NOT EXISTS `alerts` (
    `id` INT NOT NULL AUTO_INCREMENT ,
    `event_type` VARCHAR(20),
    `event_name` VARCHAR(255),
    `date` TIMESTAMP,
    `priority` INT(1),
    `custom_data` VARCHAR(2048),
    PRIMARY KEY (`id`)
);