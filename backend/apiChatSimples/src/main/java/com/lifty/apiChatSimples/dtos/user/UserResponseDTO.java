package com.lifty.apiChatSimples.dtos.user;
import com.lifty.apiChatSimples.entities.User;

public record UserResponseDTO(Long id, String name) {
    public UserResponseDTO(User user){
        this(user.getId(), user.getName());

    }
}
