package com.newestworld.streams.topic;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface ActionCreateTopicInput {

    String INPUT = "action-create";

    @Input(INPUT)
    MessageChannel input();

}
