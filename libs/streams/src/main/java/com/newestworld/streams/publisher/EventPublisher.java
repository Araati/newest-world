package com.newestworld.streams.publisher;

import com.newestworld.streams.event.Event;

public interface EventPublisher<T> {

    void send(T event);

    boolean support(Event event);
}
