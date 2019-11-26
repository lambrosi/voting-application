package com.lucasambrosi.voting.repository;

import com.lucasambrosi.voting.dto.VotingTotalizerDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VotingTotalizerRepository {

    private JdbcTemplate jdbcTemplate;

    public VotingTotalizerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<VotingTotalizerDto> getTotalizersForClosedAndNotSentSessions() {
        return jdbcTemplate.query(
                "SELECT tp.id AS 'topicId', tp.name AS 'topicName', ss.id AS 'sessionId', vt.vote AS 'vote', COUNT(vt.vote) AS 'amount' " +
                        "FROM session ss " +
                        "INNER JOIN topic tp ON (tp.id = ss.id_topic) " +
                        "INNER JOIN vote vt ON (vt.id_session = ss.id) " +
                        "WHERE ss.totalizer_sent = ? AND ss.end_date_time < NOW() " +
                        "GROUP BY tp.id, tp.name, ss.id, vt.vote, vt.vote",
                new BeanPropertyRowMapper<>(VotingTotalizerDto.class),
                Boolean.FALSE
        );
    }
}
