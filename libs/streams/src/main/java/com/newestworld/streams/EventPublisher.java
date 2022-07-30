package com.newestworld.streams;

public interface EventPublisher<T> {

    void send(T payload);
}
