package com.newestworld.streams.topic;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface ActionDeleteTopicInput {

    String INPUT = "action-delete";

    @Input(INPUT)
    MessageChannel input();

}
