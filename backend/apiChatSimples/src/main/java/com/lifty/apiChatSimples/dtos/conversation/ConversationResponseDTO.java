package com.lifty.apiChatSimples.dtos.conversation;

import com.lifty.apiChatSimples.entity.Conversation;

import java.time.LocalDateTime;

public record ConversationResponseDTO(Long id, Long senderId, String nameSender, Long receiverId, String nameReceiver, LocalDateTime created_at) {
    public ConversationResponseDTO(Conversation conversation) {
        this(
                conversation.getId(),
                conversation.getSender() != null ? conversation.getSender().getId() : null,
                conversation.getSender() != null ? conversation.getSender().getName() : null,
                conversation.getReceiver() != null ? conversation.getReceiver().getId() : null,
                conversation.getReceiver() != null ? conversation.getReceiver().getName() : null,
                conversation.getCreated_at()
        );
    }
}
