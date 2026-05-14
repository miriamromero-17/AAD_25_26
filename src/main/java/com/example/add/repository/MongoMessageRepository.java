package com.example.add.repository;

import com.example.add.model.MongoMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoMessageRepository extends MongoRepository<MongoMessage, String> {
}