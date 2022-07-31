package com.newestworld.executor.listener;

import com.newestworld.executor.service.ActionExecutorService;
import com.newestworld.streams.dto.ActionTimeoutEventDTO;
import com.newestworld.streams.topic.ActionTimeoutTopicInput;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
@EnableBinding({ActionTimeoutTopicInput.class})
@MessageEndpoint
@RequiredArgsConstructor
public class ActionTimeoutListener {

    private final ActionExecutorService service;

    @StreamListener(ActionTimeoutTopicInput.INPUT)
    public void handleActionTimeoutTopicInput(@Valid @Payload final ActionTimeoutEventDTO event) {
        for(long id : event.getActionId())  {
            service.execute(id);
        }
    }

}
