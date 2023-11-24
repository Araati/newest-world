package com.newestworld.executor.strategy;

import com.newestworld.commons.model.BasicAction;

public interface ActionExecutor {
    void exec(final BasicAction basicAction);
    boolean support(final BasicAction basicAction);
}
