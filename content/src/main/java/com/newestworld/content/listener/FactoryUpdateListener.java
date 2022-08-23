package com.newestworld.content.listener;

import com.newestworld.content.dto.FactoryUpdateDTO;
import com.newestworld.content.service.FactoryService;
import com.newestworld.commons.event.FactoryUpdateEvent;
import com.newestworld.streams.topic.FactoryUpdateTopicInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;

import javax.validation.Valid;

@Slf4j
//@Component
//@EnableBinding({FactoryUpdateTopicInput.class})
//@MessageEndpoint
@RequiredArgsConstructor
public class FactoryUpdateListener {

    // TODO: 01.08.2022 Может лучше использовать facade? В scheduler такой же вопрос
    private final FactoryService factoryService;

    // TODO: 01.08.2022 Зачем @Valid? В других listener такой же вопрос
    @StreamListener(FactoryUpdateTopicInput.INPUT)
    public void handleFactoryUpdateTopicInput(@Valid @Payload final FactoryUpdateEvent event)  {
        log.info("FactoryUpdate message received for factory {}", event.getFactoryId());
        factoryService.update(new FactoryUpdateDTO(event), event.getFactoryId());
    }

}
