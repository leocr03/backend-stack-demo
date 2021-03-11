package com.leocr.backendstackdemo.mongo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    private Message message;

    @BeforeEach
    void setUp() {
        final Integer value = 5;
        message = new Message(value);
    }

    @Test
    void testEqualsSameReference() {
        //noinspection EqualsWithItself
        final boolean equals = message.equals(message);
        //noinspection ConstantConditions
        assertTrue(equals);
    }

    @Test
    void testNotEquals() {
        final Message anotherMessage = new Message(6);
        final boolean equals = message.equals(anotherMessage);
        assertFalse(equals);
    }

    @Test
    void testEqualsDifferentClass() {
        final Integer anotherObject = 6;
        //noinspection EqualsBetweenInconvertibleTypes
        final boolean equals = message.equals(anotherObject);
        assertFalse(equals);
    }

    @Test
    void testEqualsNull() {
        //noinspection ConstantConditions
        final boolean equals = message.equals(null);
        //noinspection ConstantConditions
        assertFalse(equals);
    }
}