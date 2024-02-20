package com.newestworld.streams.publisher;

import com.newestworld.streams.event.ActionTimeoutCreateEvent;
import com.newestworld.streams.event.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;

@Slf4j
public class ActionTimeoutCreateEventPublisher extends AbstractEventPublisher<ActionTimeoutCreateEvent> {

    public ActionTimeoutCreateEventPublisher(final StreamBridge publisher, final String topic) {
        super(publisher, topic);
    }

    @Override
    public boolean support(final Event event) {
        return event.getClass().isAssignableFrom(ActionTimeoutCreateEvent.class);
    }
}
