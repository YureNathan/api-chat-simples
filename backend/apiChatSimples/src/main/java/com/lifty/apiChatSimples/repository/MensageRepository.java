package com.lifty.apiChatSimples.repository;

import com.lifty.apiChatSimples.entities.Mensage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensageRepository extends JpaRepository<Mensage, Long> {

    List<Mensage> findByConversationIdOrderBySentAtAsc(Long conversationId);
}
