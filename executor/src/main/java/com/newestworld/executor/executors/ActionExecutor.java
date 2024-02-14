package com.newestworld.executor.executors;

import com.newestworld.commons.model.BasicAction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface ActionExecutor {

    String exec(final BasicAction current, final List<BasicAction> basicActions, Map<String, String> context);

    boolean support(final BasicAction basicAction);

}
