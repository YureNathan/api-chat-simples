package com.lifty.apiChatSimples.controller;

import com.lifty.apiChatSimples.dtos.conversation.ConversationRequestDTO;
import com.lifty.apiChatSimples.dtos.conversation.ConversationResponseDTO;
import com.lifty.apiChatSimples.service.ConversationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/conversation")
public class ConversationController {

    private static final Logger logger = LoggerFactory.getLogger(ConversationController.class);

    private ConversationService conversationService;
    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }
    @GetMapping
    public ResponseEntity<List<ConversationResponseDTO>> listAllConversation(){

        logger.info("Buscando por todas as conversas");
        try{
            List<ConversationResponseDTO> conversationResponseDTOS = conversationService.listAllConversations();
            logger.info("Total de conversas encontradas: {}", conversationResponseDTOS.size());
            return ResponseEntity.ok(conversationResponseDTOS);
        } catch (Exception e) {
            logger.error("Erro ao listar conversas: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }


    }
    @PostMapping
    public ResponseEntity<ConversationResponseDTO> createConversation(@RequestBody ConversationRequestDTO conversationRequestDTO) {
        logger.info("Recebida solicitação para criar nova conversa");
        logger.debug("Payload recebido: {}", conversationRequestDTO);

        try {
            ConversationResponseDTO conversationResponseDTO = conversationService.createConversation(conversationRequestDTO);
            logger.info("Conversa criada com sucesso. ID: {}",
                    conversationResponseDTO != null ? conversationResponseDTO.id() : "null");
            return ResponseEntity.status(201).body(conversationResponseDTO);
        }catch (Exception e) {
            logger.error("Erro ao criar conversa: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }

    }
}
