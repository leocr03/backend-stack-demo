package com.leocr.backendstackdemo.common.service;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface BrokerService {

    /**
     * Produces a value to the broker.
     * @param value the value; must not be {@literal null}.
     * @return the information about the produced value; will never be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}.
     */
    @NotNull String produce(@NotNull Integer value);

    /**
     * Lists values that were consumed from the broker.
     * @param message the consumed value.
     */
    void consume(@NotNull String message);

    /**
     * Lists values that were consumed from the broker.
     * @return the list of consumed values. If not values the list will be empty.
     */
    @NotNull Set<String> list();
}
