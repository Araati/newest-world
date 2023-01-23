package com.newestworld.content.dao;

import com.newestworld.commons.exception.ResourceNotFoundException;
import com.newestworld.content.model.entity.ActionTimeoutEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ActionTimeoutRepository extends CrudRepository<ActionTimeoutEntity, Long> {
    Optional<ActionTimeoutEntity> findByActionId(final long id);
    default ActionTimeoutEntity mustFindByActionId(final long id)   {
        return findByActionId(id).orElseThrow(() -> new ResourceNotFoundException("ActionTimeout", id));
    }

    Optional<ActionTimeoutEntity> findByActionIdAndDeletedIsFalse(final long id);
    default ActionTimeoutEntity mustFindByActionIdAndDeletedIsFalse(final long id)  {
        return findByActionIdAndDeletedIsFalse(id).orElseThrow(() -> new ResourceNotFoundException("ActionTimeout", id));
    }
}

