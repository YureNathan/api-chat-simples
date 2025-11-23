package com.lifty.apiChatSimples.dtos.conversation;

import jakarta.validation.constraints.NotNull;

public record ConversationRequestDTO(@NotNull(message = "Remetente não pode ser em branco") Long idSender, @NotNull(message = "Destinatário não pode ser em branco") Long idReceiver) {

}
