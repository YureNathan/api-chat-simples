package com.lifty.apiChatSimples.dtos.mensage;

import com.lifty.apiChatSimples.entity.Mensage;

import java.time.LocalDateTime;

public record MensageResponseDTO(Long conversationId, Long idMensage, Long senderId, Long recepientId, String userName, String content, LocalDateTime sendAt) {

    public MensageResponseDTO(Mensage mensage){
        this(
                mensage.getIdConversation().getId(),
                mensage.getId(),
                mensage.getIdSender().getId(),
                mensage.getIdRecipient().getId(),
                mensage.getIdSender().getName(),
                mensage.getContent(),
                mensage.getSentAt()
        );
    }
}
