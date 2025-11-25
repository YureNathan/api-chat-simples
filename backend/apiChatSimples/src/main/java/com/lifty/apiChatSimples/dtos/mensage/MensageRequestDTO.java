package com.lifty.apiChatSimples.dtos.mensage;

public record MensageRequestDTO(Long conversationId, Long senderId, Long recipientId, String content) {
}
