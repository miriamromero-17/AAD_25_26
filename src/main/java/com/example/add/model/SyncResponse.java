package com.example.add.model;

public class SyncResponse {

    private int readFromPostgres;
    private int insertedIntoMongo;
    private int markedAsSynced;

    public SyncResponse() {
    }

    public SyncResponse(int readFromPostgres, int insertedIntoMongo, int markedAsSynced) {
        this.readFromPostgres = readFromPostgres;
        this.insertedIntoMongo = insertedIntoMongo;
        this.markedAsSynced = markedAsSynced;
    }

    public int getReadFromPostgres() {
        return readFromPostgres;
    }

    public void setReadFromPostgres(int readFromPostgres) {
        this.readFromPostgres = readFromPostgres;
    }

    public int getInsertedIntoMongo() {
        return insertedIntoMongo;
    }

    public void setInsertedIntoMongo(int insertedIntoMongo) {
        this.insertedIntoMongo = insertedIntoMongo;
    }

    public int getMarkedAsSynced() {
        return markedAsSynced;
    }

    public void setMarkedAsSynced(int markedAsSynced) {
        this.markedAsSynced = markedAsSynced;
    }
}