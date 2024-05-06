package com.newestworld.content.dao;

import com.newestworld.commons.exception.ResourceNotFoundException;
import com.newestworld.content.model.entity.ActionStructureEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ActionStructureRepository extends CrudRepository<ActionStructureEntity, Long> {

    Optional<ActionStructureEntity> findByIdAndDeletedIsFalse(final long id);
    default ActionStructureEntity mustFindByIdAndDeletedIsFalse(final long id)   {
        return findByIdAndDeletedIsFalse(id).orElseThrow(() -> new ResourceNotFoundException("ActionStructure", id));
    }

    Optional<ActionStructureEntity> findByNameAndDeletedIsFalse(final String name);
    default ActionStructureEntity mustFindByNameAndDeletedIsFalse(final String name)  {
        return findByNameAndDeletedIsFalse(name).orElseThrow(() -> new ResourceNotFoundException("ActionStructure", name));
    }
}
