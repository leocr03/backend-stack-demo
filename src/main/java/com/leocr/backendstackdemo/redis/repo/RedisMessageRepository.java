package com.leocr.backendstackdemo.redis.repo;

import com.leocr.backendstackdemo.redis.model.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisMessageRepository extends CrudRepository<Message, String> {
}
