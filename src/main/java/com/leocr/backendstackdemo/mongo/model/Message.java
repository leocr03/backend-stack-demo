package com.leocr.backendstackdemo.mongo.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Document
@Data
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
        final Date in = new Date();
        this.createdAt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Message message = (Message) o;
        return Objects.equals(value, message.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
