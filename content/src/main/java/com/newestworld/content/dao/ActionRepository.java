package com.newestworld.content.dao;

import com.newestworld.commons.exception.ResourceNotFoundException;
import com.newestworld.content.model.entity.ActionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ActionRepository extends CrudRepository<ActionEntity, Long> {

    Optional<ActionEntity> findByIdAndDeletedIsFalse(final long id);
    default ActionEntity mustFindByIdAndDeletedIsFalse(final long id)   {
        return findByIdAndDeletedIsFalse(id).orElseThrow(() -> new ResourceNotFoundException("Action", id));
    }

    default ActionEntity mustFindById(final long id)    {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("Action", id));
    }

}
