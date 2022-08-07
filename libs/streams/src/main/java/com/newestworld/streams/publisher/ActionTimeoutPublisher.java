package com.newestworld.streams.publisher;

import com.newestworld.streams.EventPublisher;
import com.newestworld.streams.dto.ActionTimeoutEventDTO;
import com.newestworld.streams.topic.ActionTimeoutTopicOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableBinding(ActionTimeoutTopicOutput.class)
@MessageEndpoint
@RequiredArgsConstructor
public class ActionTimeoutPublisher implements EventPublisher<ActionTimeoutEventDTO> {

    private final ActionTimeoutTopicOutput topic;

    @Override
    public void send(final ActionTimeoutEventDTO payload)   {
        log.info("ActionTimeout message sent for action {}", payload.getActionId());
        topic.output().send(MessageBuilder.withPayload(payload).build());
    }

}
