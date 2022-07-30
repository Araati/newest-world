package com.newestworld.streams.topic;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface ActionTimeoutTopicInput {

    String INPUT = "action-timeout";

    @Input(INPUT)
    MessageChannel input();

}
