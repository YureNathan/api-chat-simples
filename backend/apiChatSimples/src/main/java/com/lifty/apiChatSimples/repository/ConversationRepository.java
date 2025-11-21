package com.lifty.apiChatSimples.repository;

import com.lifty.apiChatSimples.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
}
