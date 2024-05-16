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
    private final StructureParameterValidationService structureParameterValidationService;
    private final AbstractObjectStructureRepository abstractObjectStructureRepository;

    public AbstractObject create(final AbstractObjectCreateDTO request) {

        final AbstractObjectStructure structure = findStructureByName(request.getName());

        Map<String, String> parameters = structureParameterValidationService.validateAndInsertDefaultIfRequired(request.getInput(), structure.getParameters());

        AbstractObjectEntity entity = new AbstractObjectEntity(request, parameters, structure);
        repository.save(entity);
        log.info("AbstractObject with {} id created", entity.getId());
        return new AbstractObjectDTO(entity);

    }

    public AbstractObject update(final AbstractObjectUpdateDTO request) {

        AbstractObjectEntity entity = repository.mustFindByIdAndDeletedIsFalse(request.getId());

        // Check if to-update parameters exist in entity
        for (final Map.Entry<String, String> pair : request.getParameters().entrySet())   {
            if (!entity.getParameters().containsKey(pair.getKey()))
                throw new ValidationFailedException();
        }

        Map<String, String> parameters = structureParameterValidationService.validateAndInsertDefaultIfRequired(request.getParameters(),
                findStructureByName(entity.getName()).getParameters());

        Map<String, String> updatedProperties = entity.getParameters();
        updatedProperties.putAll(parameters);
        entity = entity.withParameters(updatedProperties);

        repository.save(entity);
        log.info("AbstractObject with {} id updated", entity.getId());
        return new AbstractObjectDTO(entity);
    }

    public void delete(final long id) {
        repository.save(repository.mustFindByIdAndDeletedIsFalse(id).withDeleted(true));
        log.info("AbstractObject with {} id deleted", id);
    }

    public void deleteAllByStructureId(final long id)   {
        List<AbstractObjectEntity> abstractObjectEntities = repository.findAllByStructureIdAndDeletedIsFalse(id).stream().map(x -> x.withDeleted(true)).toList();
        repository.saveAll(abstractObjectEntities);
        for (AbstractObjectEntity abstractObjectEntity : abstractObjectEntities) {
            log.info("AbstractObject with {} id deleted", abstractObjectEntity.getId());
        }
    }

    public AbstractObject findById(final long id) {
        return new AbstractObjectDTO(repository.mustFindByIdAndDeletedIsFalse(id));
    }

    // Because of circular references, I'll put it here and call it a day
    private AbstractObjectStructure findStructureByName(final String name) {
        final AbstractObjectStructureEntity structureEntity = abstractObjectStructureRepository.mustFindByNameAndDeletedIsFalse(name);
        return new AbstractObjectStructureDTO(structureEntity);
    }
}
