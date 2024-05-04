package com.newestworld.content.controller.v1;

import com.newestworld.commons.model.AbstractObjectStructure;
import com.newestworld.content.dto.AbstractObjectStructureCreateDTO;
import com.newestworld.content.facade.AbstractObjectStructureFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/abstract_object/structure")
@RequiredArgsConstructor
public class AbstractObjectStructureController {

    private final AbstractObjectStructureFacade facade;

    @PostMapping
    public AbstractObjectStructure create(@Valid @RequestBody final AbstractObjectStructureCreateDTO request) {
        return facade.create(request);
    }

    // todo: update

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final long id)  {
        facade.delete(id);
    }

    @GetMapping("/{id}")
    public AbstractObjectStructure findById(@PathVariable final long id)    {
        return facade.findById(id);
    }

}
