package com.lifty.apiChatSimples.controller;

import com.lifty.apiChatSimples.dtos.conversation.ConversationRequestDTO;
import com.lifty.apiChatSimples.dtos.conversation.ConversationResponseDTO;
import com.lifty.apiChatSimples.service.ConversationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/conversation")
public class ConversationController {

    private ConversationService conversationService;
    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }
    @GetMapping
    public ResponseEntity<List<ConversationResponseDTO>> listAllConversation(){
       List<ConversationResponseDTO> conversationResponseDTOS = conversationService.listAllConversations();
       return ResponseEntity.ok(conversationResponseDTOS);
    }
    @PostMapping
    public ResponseEntity<ConversationResponseDTO> createConversation(@RequestBody ConversationRequestDTO conversationRequestDTO) {
        ConversationResponseDTO conversationResponseDTO = conversationService.createConversation(conversationRequestDTO);
        return ResponseEntity.ok(conversationResponseDTO);
    }
}
