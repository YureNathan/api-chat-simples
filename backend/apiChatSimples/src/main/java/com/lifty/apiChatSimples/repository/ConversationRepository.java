package com.lifty.apiChatSimples.repository;

import com.lifty.apiChatSimples.entities.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    void deleteAllBySender_Id(Long userId);
    void deleteAllByReceiver_Id(Long userId);

    @Query("""
       SELECT c
       FROM Conversation c
       WHERE (c.sender.id = :idUser1 AND c.receiver.id = :idUser2)
          OR (c.sender.id = :idUser2 AND c.receiver.id = :idUser1)
       """)
    Optional<Conversation> findConversationBetweenUsers(Long idUser1, Long idUser2);
}
