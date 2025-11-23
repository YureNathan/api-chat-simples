package com.lifty.apiChatSimples.repository;

import com.lifty.apiChatSimples.entity.Mensage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensageRepository extends JpaRepository<Mensage, Long> {

    List<Mensage> findByConversationOrderBySentAtAsc(Long id_conversation);
}
