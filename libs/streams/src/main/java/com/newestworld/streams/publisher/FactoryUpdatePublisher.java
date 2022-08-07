package com.newestworld.streams.publisher;

import com.newestworld.streams.EventPublisher;
import com.newestworld.streams.dto.FactoryUpdateEventDTO;
import com.newestworld.streams.topic.FactoryUpdateTopicOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableBinding(FactoryUpdateTopicOutput.class)
@MessageEndpoint
@RequiredArgsConstructor
public class FactoryUpdatePublisher implements EventPublisher<FactoryUpdateEventDTO> {

    private final FactoryUpdateTopicOutput topic;

    @Override
    public void send(final FactoryUpdateEventDTO payload)   {
        log.info("FactoryUpdate message sent for factory {}", payload.getFactoryId());
        topic.output().send(MessageBuilder.withPayload(payload).build());
    }

}
