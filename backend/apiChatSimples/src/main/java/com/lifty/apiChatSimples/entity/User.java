package com.lifty.apiChatSimples.entity;

import com.lifty.apiChatSimples.dtos.user.UserRequestDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public User() {}

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public User(UserRequestDTO userRequestDTO){
        this.name = userRequestDTO.name();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
