package com.newestworld.streams.topic;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface FactoryUpdateTopicOutput {

    String OUTPUT = "factory-update";

    @Output(OUTPUT)
    MessageChannel output();

}
