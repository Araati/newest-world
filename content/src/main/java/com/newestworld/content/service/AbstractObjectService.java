package com.newestworld.content.service;

import com.newestworld.commons.exception.ValidationFailedException;
import com.newestworld.commons.model.AbstractObject;
import com.newestworld.commons.model.AbstractObjectStructure;
import com.newestworld.commons.model.ModelParameters;
import com.newestworld.content.dao.AbstractObjectRepository;
import com.newestworld.content.dto.*;
import com.newestworld.content.model.entity.AbstractObjectEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AbstractObjectService {

    private final AbstractObjectRepository repository;
    private final ModelParameterService modelParameterService;
    private final AbstractObjectStructureService abstractObjectStructureService;

    public AbstractObject create(final AbstractObjectCreateDTO request) {

        // Validation by structure
        AbstractObjectStructure structure = abstractObjectStructureService.findByName(request.getName());
        //fixme validation
        /*
        for (StructureProperty property : structure.getProperties()) {
            if (property.getInit().isEmpty() && (request.getProperties().get(property.getName()) == null
                    || request.getProperties().get(property.getName()).isEmpty()))
                throw new ValidationFailedException();
        }*/

        //fixme save model parameters
        AbstractObjectEntity entity = new AbstractObjectEntity(request, structure);
        repository.save(entity);
        log.info("AbstractObject with {} id created", entity.getId());
        return new AbstractObjectDTO(
                entity,
                modelParameterService.findById(entity.getId())
                );

    }

    public AbstractObject update(final AbstractObjectUpdateDTO request) {

        AbstractObjectEntity entity = repository.mustFindByIdAndDeletedIsFalse(request.getId());
        ModelParameters parameters = modelParameterService.findById(entity.getId());

        // Check if to-update properties exist in entity
        for (Map.Entry<String, String> pair : request.getProperties().entrySet())   {
            if (parameters.getByName(pair.getKey()).isEmpty())
                throw new ValidationFailedException();
        }

        //fixme update with new ModelParameters
        /*
        Map<String, String> updatedProperties = entity.getParameters();
        updatedProperties.putAll(request.getProperties());
        entity = entity.withParameters(updatedProperties);
         */
        repository.save(entity);
        log.info("AbstractObject with {} id updated", entity.getId());
        return new AbstractObjectDTO(
                entity,
                modelParameterService.findById(entity.getId())
                );
    }

    public void delete(final long id) {
        repository.save(repository.mustFindByIdAndDeletedIsFalse(id).withDeleted(true));
        log.info("AbstractObject with {} id deleted", id);
    }

    public AbstractObject findById(final long id) {
        return new AbstractObjectDTO(
                repository.mustFindByIdAndDeletedIsFalse(id),
                modelParameterService.findById(id)
        );
    }
}
