package com.leocr.backendstackdemo.persistence.redis.repo;

import com.leocr.backendstackdemo.persistence.redis.model.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisMessageRepository extends CrudRepository<Message, String> {
}
