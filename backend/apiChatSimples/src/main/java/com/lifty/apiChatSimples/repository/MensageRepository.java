package com.lifty.apiChatSimples.repository;

import com.lifty.apiChatSimples.entity.Mensage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensageRepository extends JpaRepository<Mensage, Long> {
}
