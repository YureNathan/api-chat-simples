package com.lifty.apiChatSimples.service;

import com.lifty.apiChatSimples.dtos.user.UserRequestDTO;
import com.lifty.apiChatSimples.dtos.user.UserResponseDTO;
import com.lifty.apiChatSimples.entities.User;
import com.lifty.apiChatSimples.repository.ConversationRepository;
import com.lifty.apiChatSimples.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

   private UserRepository userRepository;
   private ConversationRepository conversationRepository;
   public UserService(UserRepository userRepository, ConversationRepository conversationRepository){
       this.userRepository = userRepository;
       this.conversationRepository = conversationRepository;
   }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO){
        User user = new User(userRequestDTO);
        user = userRepository.save(user);
        return new UserResponseDTO(user);
    }

    public List<UserResponseDTO> listAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(UserResponseDTO::new)
                .collect(Collectors.toList());
    }

    public UserResponseDTO findUserById(Long id){
        UserResponseDTO userResponseDTO = userRepository.findById(id)
                .stream()
                .map(UserResponseDTO::new)
                .findFirst()
                .orElseThrow(()-> new RuntimeException("Usuário não encontrado!"));
        return userResponseDTO;
    }
    @Transactional
    public void deleteUser(Long id){
        conversationRepository.deleteAllBySender_Id(id);
        conversationRepository.deleteAllByReceiver_Id(id);
        userRepository.deleteById(id);
    }
    public void updateUser(Long id, UserRequestDTO userRequestDTO){
        var userExist = userRepository.findById(id);

        if(userExist.isPresent()) {
           var user = userExist.get();
            if(userRequestDTO.name() != null) {
                user.setName(userRequestDTO.name());
            }
        }
    }

}
