package com.newestworld.scheduler.config;

import com.newestworld.commons.event.ActionDeleteEvent;
import com.newestworld.commons.model.IdReference;
import com.newestworld.commons.event.ActionTimeoutBatchEvent;
import com.newestworld.commons.event.ActionTimeoutEvent;
import com.newestworld.scheduler.service.ActionTimeoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.function.context.PollableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BeanConfig {

    @PollableBean
    public Supplier<ActionTimeoutBatchEvent> producerActionTimeoutBatchEvent(final ActionTimeoutService service) {
        return () -> {
            //todo: need to mark an action timeout like "sended" or "in-progress" to prevent duplicated event
            final List<IdReference> actionList = service.findAll(System.currentTimeMillis());
            final var eventList = actionList.stream().map(x -> new ActionTimeoutEvent(x.getId())).collect(Collectors.toList());
            if (eventList.isEmpty()) {
                log.debug("no timeout actions");
                return null;
            }

            log.debug("{} actions has timeout", eventList.size());

            return new ActionTimeoutBatchEvent(eventList);
        };
    }

    @Bean
    public Consumer<ActionDeleteEvent> consumerActionDeleteEvent(final ActionTimeoutService service) {
        return x -> {
            log.debug("Delete action timeout {}", x.getActionId());
            service.delete(x.getActionId());
        };
    }

}
