package com.newestworld.executor.controller.v1;

import com.newestworld.executor.dao.ActionParamsRepository;
import com.newestworld.executor.dao.ActionRepository;
import com.newestworld.executor.facade.ActionFacade;
import com.newestworld.executor.model.entity.ActionParamsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/action")
@RequiredArgsConstructor
public class ActionListener {

    private final ActionFacade actionFacade;

    @GetMapping
    public void executeAction(@RequestParam long id)    {
        actionFacade.execute(id);
    }

}
