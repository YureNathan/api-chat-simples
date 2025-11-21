package com.lifty.apiChatSimples.service;

import com.lifty.apiChatSimples.dtos.user.UserRequestDTO;
import com.lifty.apiChatSimples.dtos.user.UserResponseDTO;
import com.lifty.apiChatSimples.entity.User;
import com.lifty.apiChatSimples.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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

    public UserResponseDTO listUserId(Long id){
        UserResponseDTO userResponseDTO = userRepository.findById(id)
                .stream()
                .map(UserResponseDTO::new)
                .findFirst()
                .orElseThrow(null);
        return userResponseDTO;
    }
    public void deleteUser(Long id){
       userRepository.deleteById(id);
    }
}
