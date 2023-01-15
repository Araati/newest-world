package com.newestworld.executor.facade;

import com.newestworld.executor.service.ActionExecutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ActionFacade {

    private final ActionExecutorService actionExecutorService;

    public void execute(final long id) {
        actionExecutorService.execute(id);
    }
}
