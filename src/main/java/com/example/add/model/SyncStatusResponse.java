package com.example.add.model;

public class SyncStatusResponse {

    private long totalPostgres;
    private long pendingPostgres;
    private long totalMongo;

    public SyncStatusResponse() {
    }

    public SyncStatusResponse(long totalPostgres, long pendingPostgres, long totalMongo) {
        this.totalPostgres = totalPostgres;
        this.pendingPostgres = pendingPostgres;
        this.totalMongo = totalMongo;
    }

    public long getTotalPostgres() {
        return totalPostgres;
    }

    public void setTotalPostgres(long totalPostgres) {
        this.totalPostgres = totalPostgres;
    }

    public long getPendingPostgres() {
        return pendingPostgres;
    }

    public void setPendingPostgres(long pendingPostgres) {
        this.pendingPostgres = pendingPostgres;
    }

    public long getTotalMongo() {
        return totalMongo;
    }

    public void setTotalMongo(long totalMongo) {
        this.totalMongo = totalMongo;
    }
}