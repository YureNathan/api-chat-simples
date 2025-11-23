package com.lifty.apiChatSimples.service;

import com.lifty.apiChatSimples.dtos.mensage.MensageRequestDTO;
import com.lifty.apiChatSimples.dtos.mensage.MensageResponseDTO;
import com.lifty.apiChatSimples.dtos.user.UserResponseDTO;
import com.lifty.apiChatSimples.entity.Conversation;
import com.lifty.apiChatSimples.entity.Mensage;
import com.lifty.apiChatSimples.entity.User;
import com.lifty.apiChatSimples.repository.ConversationRepository;
import com.lifty.apiChatSimples.repository.MensageRepository;
import com.lifty.apiChatSimples.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MensageService {

    private MensageRepository mensageRepository;
    private UserRepository userRepository;
    private ConversationRepository conversationRepository;

    public MensageService(MensageRepository mensageRepository, UserRepository userRepository, ConversationRepository conversationRepository) {
        this.mensageRepository = mensageRepository;
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
    }

    public MensageResponseDTO sendMensage(MensageRequestDTO mensageRequestDTO) {
        Conversation conversation = conversationRepository.findById(mensageRequestDTO.conversationId())
                .orElseThrow(() -> new RuntimeException("Conversa não encontrada!.. "));

        User sender = conversation.getSender();
        User receiver = conversation.getReceiver();

        Mensage mensage = new Mensage(conversation, sender, receiver, mensageRequestDTO.content());
        mensage = mensageRepository.save(mensage);
        return new MensageResponseDTO(mensage);
    }

    public List<MensageResponseDTO> listAllMensages(){
        List<Mensage> mensages = mensageRepository.findAll();
        return mensages.stream()
                .map(MensageResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<MensageResponseDTO> listMensagesByConversationId(Long id_conversation){
        List<Mensage> mensages = mensageRepository
                .findByConversationOrderBySentAtAsc(id_conversation);
        return mensages.stream()
                .map(MensageResponseDTO::new)
                .toList();
    }
}

