package com.lifty.apiChatSimples.entity;


import com.lifty.apiChatSimples.dtos.conversation.ConversationRequestDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Table (name = "conversations")
@Entity
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @CreationTimestamp
    private LocalDateTime created_at;

    @ManyToOne
    @JoinColumn(name = "id_usuario_remetente")
    private User remetente;
    @ManyToOne
    @JoinColumn(name = "id_usuario_destinatario")
    private User destinatario;


    public Conversation(){
        this.created_at = LocalDateTime.now();
    }
    public Conversation(User remetente, User destinatario, String title){
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.title = title;
    }
    public Conversation(String title){
        this.title = title;
        this.created_at = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public User getRemetente() {
        return remetente;
    }

    public void setRemetente(User remetente) {
        this.remetente = remetente;
    }

    public User getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(User destinatario) {
        this.destinatario = destinatario;
    }
}
