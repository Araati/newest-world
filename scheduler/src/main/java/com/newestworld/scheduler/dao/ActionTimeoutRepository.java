package com.newestworld.scheduler.dao;

import com.newestworld.commons.exception.ResourceNotFoundException;
import com.newestworld.scheduler.model.entity.ActionTimeoutEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ActionTimeoutRepository extends CrudRepository<ActionTimeoutEntity, Long> {

    List<ActionTimeoutEntity> findAllByTimeoutLessThan(long timeout);

    default ActionTimeoutEntity mustFindById(final long id) {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("Action", id));
    }
}
