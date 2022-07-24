package com.worldcalls.content.facade;

import com.worldcalls.content.dto.Action;
import com.worldcalls.content.dto.ActionCreateDTO;
import com.worldcalls.content.service.ActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ActionFacade {

    private final ActionService actionService;

    public Action create(ActionCreateDTO request) {
        return actionService.create(request);
    }

    public void delete(long id) {
        actionService.delete(id);
    }

    public Action findById(long id) {
        return actionService.findById(id);
    }
}
