package com.newestworld.executor.listener;

import com.newestworld.commons.event.ActionTimeoutEvent;
import com.newestworld.commons.exception.ResourceNotFoundException;
import com.newestworld.executor.service.ActionExecutorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

import java.util.Optional;
import java.util.function.Consumer;

//@Slf4j
//@Configuration
//@RequiredArgsConstructor
//public class ActionTimeoutListenerConfiguration {
//
//    private final ActionExecutorService executor;
//
//    @Bean
//    public Consumer<Message<ActionTimeoutEvent>> actionTimeoutConsumer() {
//        return x -> {
//            log.info("ActionTimeout message received for action {}", x.getPayload().getActionId());
//            var ack = Optional.ofNullable(x.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class));
//            try {
//                executor.execute(x.getPayload().getActionId());
//                ack.ifPresent(Acknowledgment::acknowledge);
//            } catch (ResourceNotFoundException e) {
//                ack.ifPresent(Acknowledgment::acknowledge);
//            }
//        };
//    }
//}
