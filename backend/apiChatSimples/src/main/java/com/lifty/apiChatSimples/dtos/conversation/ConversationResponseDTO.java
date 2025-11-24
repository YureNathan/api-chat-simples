package com.lifty.apiChatSimples.dtos.conversation;

import com.lifty.apiChatSimples.entities.Conversation;

import java.time.LocalDateTime;

public record ConversationResponseDTO(Long id, Long senderId, String nameSender, Long receiverId, String nameReceiver, LocalDateTime created_at) {
    public ConversationResponseDTO(Conversation conversation) {
        this(
                conversation.getId(),
                conversation.getSender().getId(),
                conversation.getSender().getName(),
                conversation.getReceiver().getId(),
                conversation.getReceiver().getName(),
                conversation.getCreated_at()
        );
    }
}
