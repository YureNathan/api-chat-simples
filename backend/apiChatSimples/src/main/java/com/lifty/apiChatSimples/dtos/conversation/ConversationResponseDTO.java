package com.lifty.apiChatSimples.dtos.conversation;

import com.lifty.apiChatSimples.entity.Conversation;

import java.time.LocalDateTime;

public record ConversationResponseDTO(Long id, String title, LocalDateTime created_at) {
    public ConversationResponseDTO(Conversation conversation) {
        this(conversation.getId(), conversation.getTitle(), conversation.getCreated_at());
    }
}
