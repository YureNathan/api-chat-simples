package com.lifty.apiChatSimples.service;

import com.lifty.apiChatSimples.dtos.conversation.ConversationRequestDTO;
import com.lifty.apiChatSimples.dtos.conversation.ConversationResponseDTO;
import com.lifty.apiChatSimples.entities.Conversation;
import com.lifty.apiChatSimples.entities.User;
import com.lifty.apiChatSimples.repository.ConversationRepository;
import com.lifty.apiChatSimples.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ConversationService {

    private ConversationRepository conversationRepository;
    private UserRepository userRepository;

    public ConversationService(ConversationRepository conversationRepository, UserRepository userRepository ){
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public ConversationResponseDTO createConversation(ConversationRequestDTO conversationRequestDTO){
        User sender = findUserOrThrow(conversationRequestDTO.idSender());
        User receiver = findUserOrThrow(conversationRequestDTO.idReceiver());

        Conversation conversation = conversationRepository
                .findConversationBetweenUsers(sender.getId(), receiver.getId())
                .orElseGet(() -> conversationRepository.save(new Conversation(sender,receiver)
                ));

        return new ConversationResponseDTO(conversation);
    }

    private User findUserOrThrow(Long userId) {
        return userRepository.findById(userId).orElseThrow(()-> new RuntimeException(String
                .format("Usuário com id %d não foi encontrado", userId)));
    }

    public List<ConversationResponseDTO> listAllConversations() {
        return conversationRepository.findAll()
                .stream()
                .map(ConversationResponseDTO :: new)
               .toList();
    }
}
