package com.lifty.apiChatSimples.dtos.mensage;

import com.lifty.apiChatSimples.entities.Mensage;

import java.time.LocalDateTime;

public record MensageResponseDTO(Long conversationId, Long idMensage, Long senderId, Long recepientId, String senderName, String recipientName, String content, LocalDateTime sendAt) {

    public MensageResponseDTO(Mensage mensage){
        this(
                mensage.getIdConversation().getId(),
                mensage.getId(),
                mensage.getIdSender().getId(),
                mensage.getIdRecipient().getId(),
                mensage.getIdSender().getName(),
                mensage.getIdRecipient().getName(),
                mensage.getContent(),
                mensage.getSentAt()
        );
    }
}
