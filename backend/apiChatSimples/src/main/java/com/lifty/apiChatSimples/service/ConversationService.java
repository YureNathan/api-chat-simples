package com.lifty.apiChatSimples.service;

import com.lifty.apiChatSimples.dtos.conversation.ConversationRequestDTO;
import com.lifty.apiChatSimples.dtos.conversation.ConversationResponseDTO;
import com.lifty.apiChatSimples.entities.Conversation;
import com.lifty.apiChatSimples.entities.User;
import com.lifty.apiChatSimples.repository.ConversationRepository;
import com.lifty.apiChatSimples.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConversationService {

    private ConversationRepository conversationRepository;
    private UserRepository userRepository;

    public ConversationService(ConversationRepository conversationRepository, UserRepository userRepository ){
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    public ConversationResponseDTO createConversation(ConversationRequestDTO conversationRequestDTO){
        User sender = searchUser(conversationRequestDTO.idSender());
        User receiver = searchUser(conversationRequestDTO.idReceiver());

        Conversation conversation = conversationRepository
                .findConversationBetweenUsers(sender.getId(), receiver.getId())
                .orElseGet(() -> conversationRepository.save(new Conversation(sender,receiver)
                ));

        return new ConversationResponseDTO(conversation);
    }

    private User searchUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(()-> new RuntimeException(String
                .format("Usuário com id %d não foi encontrado", userId)));
    }

    public List<ConversationResponseDTO> listAllConversations() {
        return conversationRepository.findAll()
                .stream()
                .map(ConversationResponseDTO :: new)
                .collect(Collectors.toList());
    }
}
