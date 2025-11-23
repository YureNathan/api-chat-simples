package com.lifty.apiChatSimples.dtos.user;
import com.lifty.apiChatSimples.entity.Mensage;
import com.lifty.apiChatSimples.entity.User;

public record UserResponseDTO(Long id, String name) {
    public UserResponseDTO(User user){
        this(user.getId(), user.getName());

    }
}
