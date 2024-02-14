package com.newestworld.content.facade;

import com.newestworld.commons.model.CompoundAction;
import com.newestworld.content.dto.CompoundActionCreateDTO;
import com.newestworld.content.service.CompoundActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CompoundActionFacade {

    private final CompoundActionService compoundActionService;

    public CompoundAction create(final CompoundActionCreateDTO request) {
        return compoundActionService.create(request);
    }

    public void delete(final long id) {
        compoundActionService.delete(id);
    }

    public CompoundAction findById(final long id) {
        return compoundActionService.findById(id);
    }
}
