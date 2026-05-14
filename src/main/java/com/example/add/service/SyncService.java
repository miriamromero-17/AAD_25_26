package com.example.add.service;

import com.example.add.model.MongoMessage;
import com.example.add.model.PostgresMessage;
import com.example.add.model.SyncResponse;
import com.example.add.model.SyncStatusResponse;
import com.example.add.repository.MongoMessageRepository;
import com.example.add.repository.PostgresMessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SyncService {

    private final PostgresMessageRepository postgresMessageRepository;
    private final MongoMessageRepository mongoMessageRepository;

    public SyncService(PostgresMessageRepository postgresMessageRepository,
                       MongoMessageRepository mongoMessageRepository) {
        this.postgresMessageRepository = postgresMessageRepository;
        this.mongoMessageRepository = mongoMessageRepository;
    }

    public SyncResponse syncMessages() {
        // Primero buscamos solo los mensajes que están pendientes
        List<PostgresMessage> pendingMessages = postgresMessageRepository.findBySyncedFalse();

        int readFromPostgres = pendingMessages.size();
        int insertedIntoMongo = 0;
        int markedAsSynced = 0;

        // Recorremos los mensajes uno por uno
        for (PostgresMessage postgresMessage : pendingMessages) {
            LocalDateTime syncDate = LocalDateTime.now();

            // Creamos el documento que se va a guardar en MongoDB
            MongoMessage mongoMessage = new MongoMessage(
                    postgresMessage.getId(),
                    postgresMessage.getContent(),
                    postgresMessage.getCreatedAt(),
                    syncDate
            );

            try {
                // Guardamos primero en MongoDB
                mongoMessageRepository.save(mongoMessage);
                insertedIntoMongo++;

                // Solo si MongoDB ha funcionado, marcamos como sincronizado en PostgreSQL
                postgresMessage.setSynced(true);
                postgresMessage.setSyncedAt(syncDate);
                postgresMessageRepository.save(postgresMessage);
                markedAsSynced++;

            } catch (Exception exception) {
                // Si falla MongoDB, no se marca como sincronizado
                System.out.println("Error al sincronizar el mensaje con id " + postgresMessage.getId());
                System.out.println(exception.getMessage());
            }
        }

        return new SyncResponse(readFromPostgres, insertedIntoMongo, markedAsSynced);
    }

    public SyncStatusResponse getSyncStatus() {
        // Contamos todos los mensajes de PostgreSQL
        long totalPostgres = postgresMessageRepository.count();

        // Contamos los mensajes pendientes
        long pendingPostgres = postgresMessageRepository.countBySyncedFalse();

        // Contamos los documentos guardados en MongoDB
        long totalMongo = mongoMessageRepository.count();

        return new SyncStatusResponse(totalPostgres, pendingPostgres, totalMongo);
    }
}