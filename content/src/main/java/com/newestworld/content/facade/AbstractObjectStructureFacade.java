package com.newestworld.content.facade;

import com.newestworld.commons.model.AbstractObjectStructure;
import com.newestworld.content.dto.AbstractObjectStructureCreateDTO;
import com.newestworld.content.service.AbstractObjectStructureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AbstractObjectStructureFacade {

    private final AbstractObjectStructureService service;

    public AbstractObjectStructure create(final AbstractObjectStructureCreateDTO request) {
        return service.create(request);
    }

    public void delete(final long id) {
        service.delete(id);
    }

    public AbstractObjectStructure findById(final long id) {
        return service.findById(id);
    }

}
