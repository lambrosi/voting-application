CREATE TABLE `session` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL COMMENT 'The name of this session.',
    `id_topic` INT(11) NOT NULL COMMENT 'Topic identifier related to this session.',
    `start_date_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'The date with this session start.',
    `end_date_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'The date with this session end.',
    `totalizer_sent` TINYINT(1) NOT NULL DEFAULT 0 COMMENT 'Boolean indicating whether totalizers have already been sent.',
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_session_topic` FOREIGN KEY (`id_topic`) REFERENCES `topic` (`id`)
);