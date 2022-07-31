package com.newestworld.content.service;

import com.newestworld.content.dto.Factory;
import com.newestworld.content.dto.FactoryCreateDTO;
import com.newestworld.content.dto.FactoryDTO;
import com.newestworld.content.dao.FactoryRepository;
import com.newestworld.commons.exception.ResourceNotFoundException;
import com.newestworld.content.dto.FactoryUpdateDTO;
import com.newestworld.content.model.entity.FactoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FactoryService {

    private final FactoryRepository factoryRepository;

    // FIXME: 01.08.2022 FINAL MUST BE EVERYWHERE
    public Factory create(FactoryCreateDTO request) {
        FactoryEntity entity = new FactoryEntity(request);
        factoryRepository.save(entity);
        return new FactoryDTO(entity);
    }

    public Factory update(final FactoryUpdateDTO request, final long id)   {
        FactoryEntity entity = factoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Factory", id));

        if(request.getStore().isPresent())  {
            entity = entity.withStore(request.getStore().get()+entity.getStore());
        }

        if(request.isWorking().isPresent()) {
            entity = entity.withWorking(request.isWorking().get());
        }

        factoryRepository.save(entity);
        return new FactoryDTO(entity);
    }

    public void delete(long id) {
        FactoryEntity entity = factoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Factory", id));
        factoryRepository.delete(entity);
    }

    public Factory findById(long id) {
        return new FactoryDTO(factoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Factory", id)));
    }
}
