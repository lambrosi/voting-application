CREATE TABLE `vote` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `id_topic` INT(11) NOT NULL COMMENT 'Topic identifier related to this vote.',
    `id_session` INT(11) NOT NULL COMMENT 'Session identifier related to this vote.',
    `id_user` INT(11) NOT NULL COMMENT 'User identifier related to this vote.',
    `vote` VARCHAR(3) COMMENT 'Voting option, where \'SIM\' or \'NO\'',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`id_topic`, `id_user`),
    CONSTRAINT `fk_vote_topic` FOREIGN KEY (`id_topic`) REFERENCES `topic` (`id`),
    CONSTRAINT `fk_vote_session` FOREIGN KEY (`id_session`) REFERENCES `session` (`id`)
);