package com.newestworld.content.listener;

import com.newestworld.content.dto.ActionCreateDTO;
import com.newestworld.content.service.ActionService;
import com.newestworld.streams.dto.ActionCreateEventDTO;
import com.newestworld.streams.topic.ActionCreateTopicInput;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
@EnableBinding({ActionCreateTopicInput.class})
@MessageEndpoint
@RequiredArgsConstructor
public class ActionCreateListener {

    private final ActionService actionService;

    @StreamListener(ActionCreateTopicInput.INPUT)
    public void handleActionDeleteTopicInput(@Valid @Payload final ActionCreateEventDTO event)  {
        actionService.create(new ActionCreateDTO(event));
    }
}
