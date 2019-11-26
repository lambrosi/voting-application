package com.lucasambrosi.voting.repository;

import com.lucasambrosi.voting.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotingRepository extends JpaRepository<Vote, Long> {
}
