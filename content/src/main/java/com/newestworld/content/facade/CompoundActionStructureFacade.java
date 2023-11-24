package com.newestworld.content.facade;

import com.newestworld.commons.model.CompoundActionStructure;
import com.newestworld.content.dto.CompoundActionStructureCreateDTO;
import com.newestworld.content.service.CompoundActionStructureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CompoundActionStructureFacade {

    private final CompoundActionStructureService compoundActionStructureService;

    public CompoundActionStructure create(final CompoundActionStructureCreateDTO request) {
        return compoundActionStructureService.create(request);
    }

    public void delete(final long id) {
        compoundActionStructureService.delete(id);
    }

    public CompoundActionStructure findById(final long id) {
        return compoundActionStructureService.findById(id);
    }

}
