package com.newestworld.content.facade;

import com.newestworld.commons.model.ActionStructure;
import com.newestworld.content.dto.ActionStructureCreateDTO;
import com.newestworld.content.service.ActionStructureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ActionStructureFacade {

    private final ActionStructureService actionStructureService;

    public ActionStructure create(final ActionStructureCreateDTO request) {
        return actionStructureService.create(request);
    }

    public void delete(final long id) {
        actionStructureService.delete(id);
    }

    public ActionStructure findById(final long id) {
        return actionStructureService.findById(id);
    }

}
