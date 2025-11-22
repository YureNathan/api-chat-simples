package com.lifty.apiChatSimples.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table (name = "conversations")
@Entity
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user_sender")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "id_user_receiver")
    private User receiver;

    @OneToMany(mappedBy = "conversation")
    private List<Mensage> mensages = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime created_at;
    
    public Conversation(){
        this.created_at = LocalDateTime.now();
    }
    public Conversation(User sender, User receiver){
        this.sender = sender;
        this.receiver = receiver;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
