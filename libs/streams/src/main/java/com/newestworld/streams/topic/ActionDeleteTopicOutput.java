package com.newestworld.streams.topic;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ActionDeleteTopicOutput {

    String OUTPUT = "action-delete";

    @Output(OUTPUT)
    MessageChannel output();

}
