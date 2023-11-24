package com.newestworld.content.controller.v1;

import com.newestworld.commons.model.CompoundActionStructure;
import com.newestworld.content.dto.CompoundActionStructureCreateDTO;
import com.newestworld.content.facade.CompoundActionStructureFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/compound_action/structure")
@RequiredArgsConstructor
public class CompoundActionStructureController {

    private final CompoundActionStructureFacade compoundActionStructureFacade;

    @PostMapping
    public CompoundActionStructure create(@Valid @RequestBody final CompoundActionStructureCreateDTO request) {
        return compoundActionStructureFacade.create(request);
    }

    // TODO: 27.04.2022 update

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final long id)  {
        compoundActionStructureFacade.delete(id);
    }

    @GetMapping("/{id}")
    public CompoundActionStructure findById(@PathVariable final long id)    {
        return compoundActionStructureFacade.findById(id);
    }

}
