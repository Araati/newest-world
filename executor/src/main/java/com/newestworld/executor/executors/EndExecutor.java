package com.newestworld.executor.executors;

import com.newestworld.commons.model.ActionType;
import com.newestworld.commons.model.BasicAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class EndExecutor implements ActionExecutor {

    @Override
    public String exec(final BasicAction current, final List<BasicAction> basicActions, Map<String, String> context) {
        log.info("CompoundAction with {} id processed", Long.parseLong(context.get("compound_id")));
        return "";
    }

    @Override
    public boolean support(final BasicAction basicAction) {
        return basicAction.getType() == ActionType.END;
    }

}
