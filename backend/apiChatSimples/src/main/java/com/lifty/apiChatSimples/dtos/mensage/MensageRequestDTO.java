package com.lifty.apiChatSimples.dtos.mensage;

import com.lifty.apiChatSimples.entity.Conversation;
import com.lifty.apiChatSimples.entity.User;

public record MensageRequestDTO(Conversation idConversation, User user, String content) {
}
