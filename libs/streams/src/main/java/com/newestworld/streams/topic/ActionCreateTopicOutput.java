package com.newestworld.streams.topic;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ActionCreateTopicOutput {

    String OUTPUT = "action-create";

    @Output(OUTPUT)
    MessageChannel output();

}
