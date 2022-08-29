package com.newestworld.content.controller.v1;

import com.newestworld.commons.model.Action;
import com.newestworld.content.dto.ActionCreateDTO;
import com.newestworld.content.facade.ActionFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/action")
@RequiredArgsConstructor
public class ActionController {

    private final ActionFacade actionFacade;

    @PostMapping
    public Action create(@Valid @RequestBody final ActionCreateDTO request) {
        return actionFacade.create(request);
    }

    // TODO: 27.04.2022 update

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final long id)  {
        actionFacade.delete(id);
    }

    @GetMapping("/{id}")
    public Action findById(@PathVariable final long id)    {
        return actionFacade.findById(id);
    }

}
