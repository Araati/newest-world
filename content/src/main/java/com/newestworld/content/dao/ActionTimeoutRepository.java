package com.newestworld.content.dao;

import com.newestworld.content.model.entity.ActionTimeoutEntity;
import org.springframework.data.repository.CrudRepository;

public interface ActionTimeoutRepository extends CrudRepository<ActionTimeoutEntity, Long> {
    ActionTimeoutEntity findByActionId(long id);
}

