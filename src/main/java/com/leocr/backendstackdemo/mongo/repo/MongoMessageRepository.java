package com.leocr.backendstackdemo.mongo.repo;

import com.leocr.backendstackdemo.common.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoMessageRepository extends MongoRepository<Message, String> {
}
