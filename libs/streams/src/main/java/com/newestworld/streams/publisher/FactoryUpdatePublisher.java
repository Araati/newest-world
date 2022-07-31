package com.newestworld.streams.publisher;

import com.newestworld.streams.EventPublisher;
import com.newestworld.streams.dto.FactoryUpdateEventDTO;
import com.newestworld.streams.topic.FactoryUpdateTopicOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@EnableBinding(FactoryUpdateTopicOutput.class)
@MessageEndpoint
@RequiredArgsConstructor
public class FactoryUpdatePublisher implements EventPublisher<FactoryUpdateEventDTO> {

    private final FactoryUpdateTopicOutput topic;

    @Override
    public void send(final FactoryUpdateEventDTO payload)   {
        topic.output().send(MessageBuilder.withPayload(payload).build());
    }

}
