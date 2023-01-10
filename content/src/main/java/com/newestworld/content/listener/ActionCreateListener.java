package com.newestworld.content.listener;

import com.newestworld.commons.event.ActionCreateEvent;
import com.newestworld.content.dto.ActionCreateDTO;
import com.newestworld.content.service.ActionService;
import com.newestworld.streams.topic.ActionCreateTopicInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;

import javax.validation.Valid;

@Slf4j
//@Component
//@EnableBinding({ActionCreateTopicInput.class})
//@MessageEndpoint
@RequiredArgsConstructor
public class ActionCreateListener {

    private final ActionService actionService;

    @StreamListener(ActionCreateTopicInput.INPUT)
    public void handleActionCreateTopicInput(@Valid @Payload final ActionCreateEvent event)  {
        log.info("ActionCreate message received with type {}", event.getType());
        actionService.create(new ActionCreateDTO(event));
    }
}
