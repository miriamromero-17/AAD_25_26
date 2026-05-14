package com.example.add.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "messages_json")
public class MongoMessage {

    @Id
    private String id;

    // Este campo guarda el id original del mensaje en PostgreSQL
    private Long postgresId;

    // Contenido del mensaje
    private String content;

    // Fecha de creación original del mensaje
    private LocalDateTime createdAt;

    // Fecha en la que se sincroniza con MongoDB
    private LocalDateTime syncedAt;

    public MongoMessage() {
    }

    public MongoMessage(Long postgresId, String content, LocalDateTime createdAt, LocalDateTime syncedAt) {
        this.postgresId = postgresId;
        this.content = content;
        this.createdAt = createdAt;
        this.syncedAt = syncedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getPostgresId() {
        return postgresId;
    }

    public void setPostgresId(Long postgresId) {
        this.postgresId = postgresId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getSyncedAt() {
        return syncedAt;
    }

    public void setSyncedAt(LocalDateTime syncedAt) {
        this.syncedAt = syncedAt;
    }
}