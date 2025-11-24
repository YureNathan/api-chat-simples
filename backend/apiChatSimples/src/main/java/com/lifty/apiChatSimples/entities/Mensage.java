package com.lifty.apiChatSimples.entities;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "mensages")

public class Mensage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_conversation")
    private Conversation conversation;

    @ManyToOne
    @JoinColumn(name = "id_sender")
    private User idSender;

    @ManyToOne
    @JoinColumn(name = "id_recipient")
    private  User idRecipient;

    public User getIdRecipient() {
        return idRecipient;
    }

    public void setIdRecipient(User idRecipient) {
        this.idRecipient = idRecipient;
    }

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "date_time", nullable = false)
    @CreationTimestamp
    private LocalDateTime sentAt;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Conversation getIdConversation() {
        return conversation;
    }

    public void setIdConversation(Conversation idConversation) {
        this.conversation = idConversation;
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

    public Mensage() {}

    public Mensage(Conversation conversation, User idSender, User idRecipient, String content) {
        this.conversation = conversation;
        this.idSender = idSender;
        this.idRecipient = idRecipient;
        this.content = content;
    }
}
