package com.lucasambrosi.voting.entity;

import com.lucasambrosi.voting.enums.VoteOption;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "vote")
public class Vote implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_topic")
    private Topic topic;

    @OneToOne
    @JoinColumn(name = "id_session")
    private Session session;

    @Column
    private Long idUser;

    @Column
    @Enumerated(EnumType.STRING)
    private VoteOption vote;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public VoteOption getVote() {
        return vote;
    }

    public void setVote(VoteOption vote) {
        this.vote = vote;
    }
}
