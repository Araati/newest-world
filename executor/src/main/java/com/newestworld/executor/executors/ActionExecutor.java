package com.newestworld.executor.executors;

import com.newestworld.commons.model.Node;
import com.newestworld.executor.util.ExecutionContext;
import org.springframework.stereotype.Component;

@Component
public interface ActionExecutor {

    String exec(final ExecutionContext context);

    boolean support(final Node node);

}
