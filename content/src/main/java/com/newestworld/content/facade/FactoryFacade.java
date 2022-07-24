package com.newestworld.content.facade;

import com.newestworld.content.dto.Factory;
import com.newestworld.content.dto.FactoryCreateDTO;
import com.newestworld.content.service.FactoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FactoryFacade {

    private final FactoryService factoryService;

    public Factory create(final FactoryCreateDTO request) {
        return factoryService.create(request);
    }

    public void delete(final long id)   {
        factoryService.delete(id);
    }

    public Factory findById(long id) {
        return factoryService.findById(id);
    }
}
