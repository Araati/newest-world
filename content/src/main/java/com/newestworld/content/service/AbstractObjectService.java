package com.newestworld.content.service;

import com.newestworld.commons.exception.ValidationFailedException;
import com.newestworld.commons.model.AbstractObject;
import com.newestworld.commons.model.AbstractObjectStructure;
import com.newestworld.commons.model.StructureParameter;
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
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AbstractObjectService {

    private final AbstractObjectRepository repository;
    private final StructureParameterService structureParameterService;
    private final AbstractObjectStructureRepository abstractObjectStructureRepository;

    public AbstractObject create(final AbstractObjectCreateDTO request) {

        //fixme test this method when executor will be up

        // Validation by structure
        AbstractObjectStructureEntity structureEntity = abstractObjectStructureRepository.mustFindByNameAndDeletedIsFalse(request.getName());
        AbstractObjectStructure structure = new AbstractObjectStructureDTO(structureEntity,
                structureParameterService.findById(structureEntity.getId())
        );

        // Check, if all inputs are present
        Set<String> input = request.getInput().keySet();
        List<StructureParameter> expectedParameters = structure.getParameters();
        for (StructureParameter parameter : expectedParameters)    {
            if(!input.contains(parameter.getName()) && parameter.isRequired() && parameter.getInit() == null)   {
                throw new ValidationFailedException("Input parameter not present : " + parameter.getName());
            }
        }

        // Check, if input is valid and insert init values if needed
        Map<String, String> parameters = structureParameterService.validateAndInsertDefaultIfRequired(request.getInput(), expectedParameters);

        AbstractObjectEntity entity = new AbstractObjectEntity(request, parameters, structure);
        repository.save(entity);
        log.info("AbstractObject with {} id created", entity.getId());
        return new AbstractObjectDTO(entity);

    }

    public AbstractObject update(final AbstractObjectUpdateDTO request) {

        AbstractObjectEntity entity = repository.mustFindByIdAndDeletedIsFalse(request.getId());


        // Check if to-update properties exist in entity
        for (Map.Entry<String, String> pair : request.getParameters().entrySet())   {
            if (!entity.getParameters().containsKey(pair.getKey()))
                throw new ValidationFailedException();
        }

        // Validation by structure
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
