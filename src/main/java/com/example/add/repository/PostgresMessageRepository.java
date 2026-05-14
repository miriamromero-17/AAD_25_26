package com.example.add.repository;

import com.example.add.model.PostgresMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostgresMessageRepository extends JpaRepository<PostgresMessage, Long> {

    // Busca solo los mensajes que todavía no se han sincronizado
    List<PostgresMessage> findBySyncedFalse();

    // Cuenta los mensajes que quedan pendientes
    long countBySyncedFalse();
}