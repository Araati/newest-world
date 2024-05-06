package com.newestworld.content.service;

import com.newestworld.commons.model.AbstractObjectStructure;
import com.newestworld.commons.model.ModelParameters;
import com.newestworld.content.dao.AbstractObjectStructureRepository;
import com.newestworld.content.dto.AbstractObjectStructureCreateDTO;
import com.newestworld.content.dto.AbstractObjectStructureDTO;
import com.newestworld.content.model.entity.AbstractObjectStructureEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AbstractObjectStructureService {

    private final AbstractObjectStructureRepository repository;
    private final ModelParameterService modelParameterService;

    public AbstractObjectStructure create(final AbstractObjectStructureCreateDTO request) {
        AbstractObjectStructureEntity entity = new AbstractObjectStructureEntity(request);
        repository.save(entity);
        ModelParameters parameters = modelParameterService.create(entity.getId(), request.getParameters());
        log.info("AbstractObjectStructure with {} id created", entity.getId());
        return new AbstractObjectStructureDTO(
                entity,
                parameters
        );
    }

    public void delete(final long id) {
        repository.save(repository.mustFindByIdAndDeletedIsFalse(id).withDeleted(true));
        // todo: must delete all existing object with this structureId
        log.info("AbstractObjectStructure with {} id deleted", id);
    }

    public AbstractObjectStructure findById(final long id) {
        return new AbstractObjectStructureDTO(
                repository.mustFindByIdAndDeletedIsFalse(id),
                modelParameterService.findById(id)
                );
    }

    public AbstractObjectStructure findByName(final String name)    {
        AbstractObjectStructureEntity entity = repository.mustFindByNameAndDeletedIsFalse(name);
        return new AbstractObjectStructureDTO(
                entity,
                modelParameterService.findById(entity.getId())
                );
    }

}
