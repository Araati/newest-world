package com.newestworld.streams.publisher;

import com.newestworld.streams.EventPublisher;
import com.newestworld.streams.dto.ActionCreateEventDTO;
import com.newestworld.streams.topic.ActionCreateTopicOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@EnableBinding(ActionCreateTopicOutput.class)
@MessageEndpoint
@RequiredArgsConstructor
public class ActionCreatePublisher implements EventPublisher<ActionCreateEventDTO> {

    private final ActionCreateTopicOutput topic;

    @Override
    public void send(final ActionCreateEventDTO payload)    {
        topic.output().send(MessageBuilder.withPayload(payload).build());
    }

}
