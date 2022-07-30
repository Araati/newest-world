package com.newestworld.streams.topic;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ActionTimeoutTopicOutput {

    String OUTPUT = "action.timeout";

    @Output(OUTPUT)
    MessageChannel output();

}
