package com.newestworld.content.controller.v1;

import com.newestworld.commons.model.ActionStructure;
import com.newestworld.content.dto.ActionStructureCreateDTO;
import com.newestworld.content.facade.ActionStructureFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/action/structure")
@RequiredArgsConstructor
public class ActionStructureController {

    private final ActionStructureFacade actionStructureFacade;

    @PostMapping
    public ActionStructure create(@Valid @RequestBody final ActionStructureCreateDTO request) {
        return actionStructureFacade.create(request);
    }

    // TODO: 27.04.2022 update

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final long id)  {
        actionStructureFacade.delete(id);
    }

    @GetMapping("/{id}")
    public ActionStructure findById(@PathVariable final long id)    {
        return actionStructureFacade.findById(id);
    }

}
