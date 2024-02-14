package com.newestworld.content.controller.v1;

import com.newestworld.commons.model.CompoundAction;
import com.newestworld.content.dto.CompoundActionCreateDTO;
import com.newestworld.content.facade.CompoundActionFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/compound_action")
@RequiredArgsConstructor
public class CompoundActionController {

    private final CompoundActionFacade compoundActionFacade;

    @PostMapping
    public CompoundAction create(@Valid @RequestBody final CompoundActionCreateDTO request) {
        return compoundActionFacade.create(request);
    }

    // TODO: 25.11.2023 update

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final long id)  {
        compoundActionFacade.delete(id);
    }

    @GetMapping("/{id}")
    public CompoundAction findById(@PathVariable final long id)    {
        return compoundActionFacade.findById(id);
    }

}
