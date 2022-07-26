package com.newestworld.scheduler.dao;

import com.newestworld.scheduler.model.entity.ActionTimeoutEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ActionTimeoutRepository extends CrudRepository<ActionTimeoutEntity, Long> {

    List<ActionTimeoutEntity> findAllByTimeoutLessThan(long timeout);
}
