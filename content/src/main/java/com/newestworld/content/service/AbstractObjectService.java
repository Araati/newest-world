package com.newestworld.content.service;

import com.newestworld.commons.exception.ValidationFailedException;
import com.newestworld.commons.model.AbstractObject;
import com.newestworld.commons.model.AbstractObjectStructure;
import com.newestworld.commons.model.ModelParameter;
import com.newestworld.commons.model.StructureParameter;
import com.newestworld.content.dao.AbstractObjectRepository;
import com.newestworld.content.dao.AbstractObjectStructureRepository;
import com.newestworld.content.dto.*;
import com.newestworld.content.model.entity.AbstractObjectEntity;
import com.newestworld.content.model.entity.AbstractObjectStructureEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AbstractObjectService {

    private final Validator validator;
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
            if(!input.contains(parameter.getName()) && parameter.isRequired())   {
                throw new ValidationFailedException("Input parameter not present : " + parameter.getName());
            }
        }

        // Check, if input is valid and insert init values if needed
        //fixme code duplicate
        Map<String, String> inputMap = request.getInput();
        Map<String, String> parameters = new HashMap<>();
        for (StructureParameter parameter : expectedParameters) {
            var validatableBuilder = ModelParameter.builder();
            Set<ConstraintViolation<ModelParameter>> violations;
            validatableBuilder
                    .name(parameter.getName())
                    .required(parameter.isRequired())
                    .type(parameter.getType())
                    .min(parameter.getMin())
                    .max(parameter.getMax());
            if (inputMap.containsKey(parameter.getName())) {
                validatableBuilder.data(inputMap.get(parameter.getName()));
            } else if (parameter.getInit() != null) {
                validatableBuilder.data(parameter.getInit());
            } else if (parameter.isRequired()) {
                throw new ValidationFailedException("Input parameter not present : " + parameter.getName());
            }
            var validatable = validatableBuilder.build();
            violations = validator.validate(validatableBuilder.build());
            if (!violations.isEmpty()) {
                throw new ValidationFailedException();
            }
            parameters.put(parameter.getName(), validatable.getData());
        }

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

        Map<String, String> updatedProperties = entity.getParameters();
        updatedProperties.putAll(request.getParameters());
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
