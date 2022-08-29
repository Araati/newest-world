package com.newestworld.content.listener;

import com.newestworld.commons.event.ActionDeleteEvent;
import com.newestworld.content.service.ActionService;
import com.newestworld.streams.topic.ActionDeleteTopicInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;

import javax.validation.Valid;

@Slf4j
//@Component
//@EnableBinding({ActionDeleteTopicInput.class})
//@MessageEndpoint
@RequiredArgsConstructor
public class ActionDeleteListener {

    // TODO: 01.08.2022 Может лучше использовать facade? В scheduler такой же вопрос
    private final ActionService actionService;

    // TODO: 01.08.2022 Зачем @Valid? В других listener такой же вопрос
    @StreamListener(ActionDeleteTopicInput.INPUT)
    public void handleActionDeleteTopicInput(@Valid @Payload final ActionDeleteEvent event)  {
        log.info("ActionDelete message received for action {}", event.getActionId());
        actionService.delete(event.getActionId());
    }

}
