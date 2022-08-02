package com.newestworld.streams.publisher;

import com.newestworld.streams.EventPublisher;
import com.newestworld.streams.dto.ActionDeleteEventDTO;
import com.newestworld.streams.topic.ActionDeleteTopicOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@EnableBinding(ActionDeleteTopicOutput.class)
@MessageEndpoint
@RequiredArgsConstructor
public class ActionDeletePublisher implements EventPublisher<ActionDeleteEventDTO> {

    private final ActionDeleteTopicOutput topic;

    @Override
    public void send(final ActionDeleteEventDTO payload)   {
        topic.output().send(MessageBuilder.withPayload(payload).build());
    }

}
