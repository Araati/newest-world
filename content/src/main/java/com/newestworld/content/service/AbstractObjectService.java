package com.newestworld.content.service;

import com.newestworld.commons.exception.ValidationFailedException;
import com.newestworld.commons.model.AbstractObject;
import com.newestworld.commons.model.AbstractObjectStructure;
import com.newestworld.commons.model.ModelParameters;
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
    private final ModelParameterService modelParameterService;
    private final AbstractObjectStructureRepository abstractObjectStructureRepository;

    public AbstractObject create(final AbstractObjectCreateDTO request) {

        // Validation by structure
        AbstractObjectStructureEntity structureEntity = abstractObjectStructureRepository.mustFindByNameAndDeletedIsFalse(request.getName());
        AbstractObjectStructure structure = new AbstractObjectStructureDTO(structureEntity,
                modelParameterService.findById(structureEntity.getId())
        );
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
        modelParameterService.delete(id);
        log.info("AbstractObject with {} id deleted", id);
    }

    public void deleteAllByStructureId(final long id)   {
        List<AbstractObjectEntity> abstractObjectEntities = repository.findAllByStructureIdAndDeletedIsFalse(id).stream().map(x -> x.withDeleted(true)).toList();
        repository.saveAll(abstractObjectEntities);
        modelParameterService.deleteAll(abstractObjectEntities.stream().map(AbstractObjectEntity::getId).toList());
        for (AbstractObjectEntity abstractObjectEntity : abstractObjectEntities) {
            log.info("AbstractObject with {} id deleted", abstractObjectEntity.getId());
        }
    }

    public AbstractObject findById(final long id) {
        return new AbstractObjectDTO(
                repository.mustFindByIdAndDeletedIsFalse(id),
                modelParameterService.findById(id)
        );
    }
}
