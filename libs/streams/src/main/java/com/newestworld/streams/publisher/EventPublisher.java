package com.newestworld.streams.publisher;

public interface EventPublisher<T> {

    void send(T event);
}
