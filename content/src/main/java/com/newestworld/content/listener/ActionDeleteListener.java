package com.newestworld.content.listener;

import com.newestworld.content.service.ActionService;
import com.newestworld.streams.dto.ActionDeleteEventDTO;
import com.newestworld.streams.topic.ActionDeleteTopicInput;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
@EnableBinding({ActionDeleteTopicInput.class})
@MessageEndpoint
@RequiredArgsConstructor
public class ActionDeleteListener {

    // TODO: 01.08.2022 Может лучше использовать facade? В scheduler такой же вопрос
    private final ActionService actionService;

    // TODO: 01.08.2022 Зачем @Valid? В других listener такой же вопрос
    @StreamListener(ActionDeleteTopicInput.INPUT)
    public void handleActionDeleteTopicInput(@Valid @Payload final ActionDeleteEventDTO event)  {
        actionService.delete(event.getActionId());
    }

}
