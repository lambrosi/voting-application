package com.lucasambrosi.voting.repository;

import com.lucasambrosi.voting.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query(value = "SELECT * FROM session WHERE id_topic = :topicId", nativeQuery = true)
    List<Session> findByTopicId(@Param("topicId") Long topicId);
}
