package com.newestworld.executor.config;

import com.newestworld.commons.event.ActionTimeoutBatchEvent;
import com.newestworld.commons.event.ActionTimeoutEvent;
import com.newestworld.executor.service.ActionExecutorAggregator;
import com.newestworld.executor.service.ActionExecutorService;
import com.newestworld.executor.service.ActionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BeanConfig {

    @Bean
    public Consumer<ActionTimeoutBatchEvent> consumerActionTimeoutBatchEvent(
            final ActionService actionService,
            final ActionExecutorAggregator aggregator
    ) {
        return x -> {
            log.debug("received {} actions for execution", x.getSize());
            var list = actionService.findAllByIds(x.getBatch().stream()
                    .map(ActionTimeoutEvent::getId)
                    .collect(Collectors.toList()));

            aggregator.execute(list);
        };
    }
}
