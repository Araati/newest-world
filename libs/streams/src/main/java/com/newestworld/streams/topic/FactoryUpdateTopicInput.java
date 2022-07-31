package com.newestworld.streams.topic;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface FactoryUpdateTopicInput {

    String INPUT = "factory-update";

    @Input(INPUT)
    MessageChannel input();

}
