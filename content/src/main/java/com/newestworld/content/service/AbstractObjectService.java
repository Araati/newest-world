package com.newestworld.content.service;

import com.newestworld.commons.exception.ValidationFailedException;
import com.newestworld.commons.model.AbstractObject;
import com.newestworld.commons.model.AbstractObjectStructure;
import com.newestworld.content.dao.AbstractObjectRepository;
import com.newestworld.content.dao.AbstractObjectStructureRepository;
import com.newestworld.content.dto.*;
import com.newestworld.content.model.entity.AbstractObjectEntity;
import com.newestworld.content.model.entity.AbstractObjectStructureEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AbstractObjectService {

    private final AbstractObjectRepository repository;
    private final StructureParameterService structureParameterService;
    private final AbstractObjectStructureRepository abstractObjectStructureRepository;

    public AbstractObject create(final AbstractObjectCreateDTO request) {

        AbstractObjectStructureEntity structureEntity = abstractObjectStructureRepository.mustFindByNameAndDeletedIsFalse(request.getName());
        AbstractObjectStructure structure = new AbstractObjectStructureDTO(structureEntity,
                structureParameterService.findById(structureEntity.getId())
        );

        Map<String, String> parameters = structureParameterService.validateAndInsertDefaultIfRequired(request.getInput(), structure.getParameters());

        AbstractObjectEntity entity = new AbstractObjectEntity(request, parameters, structure);
        repository.save(entity);
        log.info("AbstractObject with {} id created", entity.getId());
        return new AbstractObjectDTO(entity);

    }

    public AbstractObject update(final AbstractObjectUpdateDTO request) {

        AbstractObjectEntity entity = repository.mustFindByIdAndDeletedIsFalse(request.getId());

        // Check if to-update parameters exist in entity
        for (Map.Entry<String, String> pair : request.getParameters().entrySet())   {
            if (!entity.getParameters().containsKey(pair.getKey()))
                throw new ValidationFailedException();
        }

        AbstractObjectStructureEntity structureEntity = abstractObjectStructureRepository.mustFindByNameAndDeletedIsFalse(entity.getName());
        AbstractObjectStructure structure = new AbstractObjectStructureDTO(structureEntity,
                structureParameterService.findById(structureEntity.getId())
        );

        Map<String, String> parameters = structureParameterService.validateAndInsertDefaultIfRequired(request.getParameters(), structure.getParameters());

        Map<String, String> updatedProperties = entity.getParameters();
        updatedProperties.putAll(parameters);
        entity = entity.withParameters(updatedProperties);

        repository.save(entity);
        log.info("AbstractObject with {} id updated", entity.getId());
        return new AbstractObjectDTO(entity);
    }

    public void delete(final long id) {
        repository.save(repository.mustFindByIdAndDeletedIsFalse(id).withDeleted(true));
        structureParameterService.delete(id);
        log.info("AbstractObject with {} id deleted", id);
    }

    public void deleteAllByStructureId(final long id)   {
        List<AbstractObjectEntity> abstractObjectEntities = repository.findAllByStructureIdAndDeletedIsFalse(id).stream().map(x -> x.withDeleted(true)).toList();
        repository.saveAll(abstractObjectEntities);
        structureParameterService.deleteAll(abstractObjectEntities.stream().map(AbstractObjectEntity::getId).toList());
        for (AbstractObjectEntity abstractObjectEntity : abstractObjectEntities) {
            log.info("AbstractObject with {} id deleted", abstractObjectEntity.getId());
        }
    }

    public AbstractObject findById(final long id) {
        return new AbstractObjectDTO(repository.mustFindByIdAndDeletedIsFalse(id));
    }
}
