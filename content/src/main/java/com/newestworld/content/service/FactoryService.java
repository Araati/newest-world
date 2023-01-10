package com.newestworld.content.service;

import com.newestworld.commons.exception.ResourceNotFoundException;
import com.newestworld.content.dao.FactoryRepository;
import com.newestworld.content.dto.Factory;
import com.newestworld.content.dto.FactoryCreateDTO;
import com.newestworld.content.dto.FactoryDTO;
import com.newestworld.content.dto.FactoryUpdateDTO;
import com.newestworld.content.model.entity.FactoryEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FactoryService {

    private final FactoryRepository factoryRepository;

    // FIXME: 01.08.2022 FINAL MUST BE EVERYWHERE
    public Factory create(FactoryCreateDTO request) {
        FactoryEntity entity = new FactoryEntity(request);
        factoryRepository.save(entity);
        log.info("Factory with {} id created", entity.getId());
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
        log.info("Factory with {} id updated", entity.getId());
        return new FactoryDTO(entity);
    }

    public void delete(long id) {
        FactoryEntity entity = factoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Factory", id));
        factoryRepository.delete(entity);
        log.info("Factory with {} id deleted", entity.getId());
    }

    public Factory findById(long id) {
        return new FactoryDTO(factoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Factory", id)));
    }
}
