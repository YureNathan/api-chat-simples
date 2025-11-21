package com.lifty.apiChatSimples.service;

import com.lifty.apiChatSimples.dtos.conversation.ConversationRequestDTO;
import com.lifty.apiChatSimples.dtos.conversation.ConversationResponseDTO;
import com.lifty.apiChatSimples.entity.Conversation;
import com.lifty.apiChatSimples.entity.User;
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
       User remetente = buscaUsuario(conversationRequestDTO.remetente());
       User destinatario = buscaUsuario(conversationRequestDTO.destinatario());
       Conversation conversation = new Conversation(remetente,destinatario,conversationRequestDTO.title());
        conversation = conversationRepository.save(conversation);
       return new ConversationResponseDTO(conversation);
    }

    private User buscaUsuario(Long userId) {
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
