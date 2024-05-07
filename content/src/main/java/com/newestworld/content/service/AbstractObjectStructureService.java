package com.newestworld.content.service;

import com.newestworld.commons.model.AbstractObjectStructure;
import com.newestworld.commons.model.StructureParameter;
import com.newestworld.content.dao.AbstractObjectStructureRepository;
import com.newestworld.content.dto.AbstractObjectStructureCreateDTO;
import com.newestworld.content.dto.AbstractObjectStructureDTO;
import com.newestworld.content.model.entity.AbstractObjectStructureEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AbstractObjectStructureService {

    private final AbstractObjectStructureRepository repository;
    private final StructureParameterService structureParameterService;
    private final AbstractObjectService abstractObjectService;

    public AbstractObjectStructure create(final AbstractObjectStructureCreateDTO request) {
        AbstractObjectStructureEntity entity = new AbstractObjectStructureEntity(request);
        repository.save(entity);
        List<StructureParameter> parameters = new ArrayList<>(structureParameterService.create(entity.getId(), request.getParameters()));
        log.info("AbstractObjectStructure with {} id created", entity.getId());
        return new AbstractObjectStructureDTO(
                entity,
                parameters
        );
    }

    public void delete(final long id) {
        repository.save(repository.mustFindByIdAndDeletedIsFalse(id).withDeleted(true));
        structureParameterService.delete(id);
        abstractObjectService.deleteAllByStructureId(id);
        log.info("AbstractObjectStructure with {} id deleted", id);
    }

    public AbstractObjectStructure findById(final long id) {
        return new AbstractObjectStructureDTO(
                repository.mustFindByIdAndDeletedIsFalse(id),
                structureParameterService.findById(id)
                );
    }

    public AbstractObjectStructure findByName(final String name)    {
        AbstractObjectStructureEntity entity = repository.mustFindByNameAndDeletedIsFalse(name);
        return new AbstractObjectStructureDTO(
                entity,
                structureParameterService.findById(entity.getId())
                );
    }

}
