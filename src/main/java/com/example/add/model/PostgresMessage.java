package com.example.add.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class PostgresMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Contenido del mensaje que está guardado en PostgreSQL
    @Column(name = "content", nullable = false)
    private String content;

    // Fecha en la que se creó el mensaje
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Indica si el mensaje ya se ha pasado a MongoDB
    @Column(name = "synced", nullable = false)
    private Boolean synced;

    // Fecha en la que se sincronizó con MongoDB
    @Column(name = "synced_at")
    private LocalDateTime syncedAt;

    public PostgresMessage() {
    }

    public PostgresMessage(Long id, String content, LocalDateTime createdAt, Boolean synced, LocalDateTime syncedAt) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.synced = synced;
        this.syncedAt = syncedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getSynced() {
        return synced;
    }

    public void setSynced(Boolean synced) {
        this.synced = synced;
    }

    public LocalDateTime getSyncedAt() {
        return syncedAt;
    }

    public void setSyncedAt(LocalDateTime syncedAt) {
        this.syncedAt = syncedAt;
    }
}