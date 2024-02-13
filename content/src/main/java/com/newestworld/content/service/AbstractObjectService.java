package com.newestworld.content.service;

import com.newestworld.commons.exception.ValidationFailedException;
import com.newestworld.commons.model.AbstractObject;
import com.newestworld.commons.model.AbstractObjectStructure;
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
    private final AbstractObjectStructureService abstractObjectStructureService;

    // fixme: not tested, because there's no basicAction for creating objects now
    public AbstractObject create(final AbstractObjectCreateDTO request) {

        // Validation by structure
        AbstractObjectStructure structure = abstractObjectStructureService.findByName(request.getName());
        for (Map.Entry<String, String> pair : structure.getProperties().entrySet()) {
            if (pair.getValue().isEmpty() && (request.getProperties().get(pair.getValue()) == null
                    || request.getProperties().get(pair.getValue()).isEmpty()))
                throw new ValidationFailedException();
        }

        AbstractObjectEntity entity = new AbstractObjectEntity(request, structure.getId());
        repository.save(entity);
        log.info("AbstractObject with {} id created", entity.getId());
        return new AbstractObjectDTO(entity);
    }

    public void delete(final long id) {
        repository.save(repository.mustFindByIdAndDeletedIsFalse(id).withDeleted(true));
        log.info("AbstractObject with {} id deleted", id);
    }

    public AbstractObject findById(final long id) {
        return new AbstractObjectDTO(repository.mustFindByIdAndDeletedIsFalse(id));
    }
}
