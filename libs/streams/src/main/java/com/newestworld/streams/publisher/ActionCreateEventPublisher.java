package com.newestworld.streams.publisher;

import com.newestworld.streams.event.ActionCreateEvent;
import com.newestworld.streams.event.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;

@Slf4j
public class ActionCreateEventPublisher extends AbstractEventPublisher<ActionCreateEvent> {

    public ActionCreateEventPublisher(final StreamBridge publisher, final String topic) {
        super(publisher, topic);
    }

    @Override
    public boolean support(final Event event) {
        return event.getClass().isAssignableFrom(ActionCreateEvent.class);
    }
}
