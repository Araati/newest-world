package com.newestworld.content.dao;

import com.newestworld.commons.exception.ResourceNotFoundException;
import com.newestworld.content.model.entity.AbstractObjectStructureEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AbstractObjectStructureRepository extends CrudRepository<AbstractObjectStructureEntity, Long> {
    Optional<AbstractObjectStructureEntity> findByIdAndDeletedIsFalse(final long id);
    default AbstractObjectStructureEntity mustFindByIdAndDeletedIsFalse(final long id)   {
        return findByIdAndDeletedIsFalse(id).orElseThrow(() -> new ResourceNotFoundException("AbstractObjectStructure", id));
    }

    Optional<AbstractObjectStructureEntity> findByNameAndDeletedIsFalse(final String name);
    default AbstractObjectStructureEntity mustFindByNameAndDeletedIsFalse(final String name)  {
        return findByNameAndDeletedIsFalse(name).orElseThrow(() -> new ResourceNotFoundException("AbstractObjectStructure", name));
    }

}
