package com.newestworld.executor.executors;

import com.newestworld.commons.model.ActionType;
import com.newestworld.commons.model.Node;
import com.newestworld.executor.util.ExecutionContext;
import com.newestworld.streams.publisher.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EndExecutor implements ActionExecutor {

    private final List<EventPublisher<?>> publishers;

    @Override
    public String exec(final ExecutionContext context) {
        var events = context.getEvents();
        for (var event : events)    {
            for (var publisher : publishers)    {
                if (publisher.support(event))
                    publisher.send(event);
            }
        }

        log.info("CompoundAction with {} id processed", Long.parseLong(context.getLocalVariable("compound_id").toString()));
        return "";
    }

    @Override
    public boolean support(final Node node) {
        return node.getType() == ActionType.END;
    }

}
