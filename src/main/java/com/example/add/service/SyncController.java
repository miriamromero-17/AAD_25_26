package com.example.add.controller;

import com.example.add.model.SyncResponse;
import com.example.add.model.SyncStatusResponse;
import com.example.add.service.SyncService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SyncController {

    private final SyncService syncService;

    public SyncController(SyncService syncService) {
        this.syncService = syncService;
    }

    // Endpoint para ejecutar la sincronización
    @PostMapping("/sync")
    public SyncResponse syncMessages() {
        return syncService.syncMessages();
    }

    // Endpoint para ver el estado de la sincronización
    @GetMapping("/sync/status")
    public SyncStatusResponse getSyncStatus() {
        return syncService.getSyncStatus();
    }
}