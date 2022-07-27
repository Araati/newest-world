package com.newestworld.executor.strategy;

import com.newestworld.commons.dto.Action;

public interface ActionExecutor {
    void exec(final Action action);
    boolean support(final Action action);
}
