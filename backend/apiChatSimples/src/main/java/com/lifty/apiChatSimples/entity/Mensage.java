package com.lifty.apiChatSimples.entity;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "mensages")

public class Mensage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne
    @JoinColumn(name = "id_conversation")
    private Conversation idConversation;

    @ManyToOne
    @JoinColumn(name = "id_sender")
    private User idSender;

    @CreationTimestamp
    private LocalDateTime sentAt;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Conversation getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(Conversation idConversation) {
        this.idConversation = idConversation;
    }

    public User getIdSender() {
        return idSender;
    }

    public void setIdSender(User idSender) {
        this.idSender = idSender;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
