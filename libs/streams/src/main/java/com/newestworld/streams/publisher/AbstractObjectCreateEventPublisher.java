package com.newestworld.streams.publisher;

import com.newestworld.streams.event.AbstractObjectCreateEvent;
import com.newestworld.streams.event.Event;
import org.springframework.cloud.stream.function.StreamBridge;

public class AbstractObjectCreateEventPublisher extends AbstractEventPublisher<AbstractObjectCreateEvent> {

    public AbstractObjectCreateEventPublisher(final StreamBridge publisher, final String topic) {
        super(publisher, topic);
    }

    @Override
    public boolean support(final Event event) {
        return event.getClass().isAssignableFrom(AbstractObjectCreateEvent.class);
    }
}
