package com.lifty.apiChatSimples.dtos.mensage;

import com.lifty.apiChatSimples.entity.Mensage;

public record MensageResponseDTO(Long ConversationId, Long idMensage, Long senderId, Long recepientId, String userName, String content) {

    public MensageResponseDTO(Mensage mensage){
        this(
                mensage.getIdConversation().getId(),
                mensage.getId(),
                mensage.getIdSender().getId(),
                mensage.getIdRecipient().getId(),
                mensage.getIdRecipient().getName(),
                mensage.getContent()

        );
    }
}
