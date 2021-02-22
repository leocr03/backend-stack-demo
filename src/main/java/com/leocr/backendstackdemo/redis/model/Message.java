package com.leocr.backendstackdemo.redis.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Data
@RedisHash("Message")
public class Message implements Serializable {

    @Id
    @Getter
    private final Integer value;

    @Getter
    @Setter
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;

    public Message(Integer value) {
        this.value = value;
        Date in = new Date();
        this.createdAt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(value, message.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
