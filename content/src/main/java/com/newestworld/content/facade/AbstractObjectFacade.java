package com.newestworld.content.facade;

import com.newestworld.commons.model.AbstractObject;
import com.newestworld.content.dto.AbstractObjectCreateDTO;
import com.newestworld.content.dto.AbstractObjectUpdateDTO;
import com.newestworld.content.service.AbstractObjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AbstractObjectFacade {

    private final AbstractObjectService service;

    public AbstractObject create(final AbstractObjectCreateDTO request) {
        return service.create(request);
    }

    public AbstractObject update(final AbstractObjectUpdateDTO request) {
        return service.update(request);
    }
    //todo: update

    public void delete(final long id) {
        service.delete(id);
    }

    public AbstractObject findById(final long id) {
        return service.findById(id);
    }

}
