package com.worldcalls.content.service;

import com.worldcalls.content.dao.FactoryRepository;
import com.worldcalls.content.dto.Factory;
import com.worldcalls.content.dto.FactoryCreateDTO;
import com.worldcalls.content.dto.FactoryDTO;
import com.worldcalls.content.exception.ResourceNotFoundException;
import com.worldcalls.content.model.entity.FactoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FactoryService {

    private final FactoryRepository factoryRepository;

    public Factory create(FactoryCreateDTO request) {
        FactoryEntity entity = new FactoryEntity(request);
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
