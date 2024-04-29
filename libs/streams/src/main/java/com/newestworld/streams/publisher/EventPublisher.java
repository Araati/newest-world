package com.newestworld.streams.publisher;

import com.newestworld.streams.event.Event;

public interface EventPublisher<T> {

    void send(Event event);

    boolean support(Event event);
}
