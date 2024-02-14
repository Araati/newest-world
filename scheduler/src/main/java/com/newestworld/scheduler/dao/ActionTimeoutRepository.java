package com.newestworld.scheduler.dao;

import com.newestworld.commons.exception.ResourceNotFoundException;
import com.newestworld.scheduler.model.entity.ActionTimeoutEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ActionTimeoutRepository extends CrudRepository<ActionTimeoutEntity, Long> {

    List<ActionTimeoutEntity> findAllByTimeoutLessThanAndDeletedIsFalseAndProcessingIsFalse(final long timeout);

    Optional<ActionTimeoutEntity> findByActionId(final long actionId);
    default ActionTimeoutEntity mustFindByActionId(final long id) {
        return findByActionId(id).orElseThrow(() -> new ResourceNotFoundException("ActionTimeout", id));
    }
}
