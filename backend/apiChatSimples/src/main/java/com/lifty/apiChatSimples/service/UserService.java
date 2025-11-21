package com.lifty.apiChatSimples.service;

import com.lifty.apiChatSimples.dtos.UserRequestDTO;
import com.lifty.apiChatSimples.dtos.UserResponseDTO;
import com.lifty.apiChatSimples.entity.User;
import com.lifty.apiChatSimples.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

   private UserRepository userRepository;

   public UserService(UserRepository userRepository){
       this.userRepository = userRepository;
   }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO){
        User user = new User(userRequestDTO);
        user = userRepository.save(user);
        return new UserResponseDTO(user);
    }

    public List<UserResponseDTO> listAllUser(){
        return userRepository.findAll()
                .stream()
                .map(UserResponseDTO::new)
                .collect(Collectors.toList());
    }

}
