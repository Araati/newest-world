package com.worldcalls.content.dao;

import com.worldcalls.content.model.entity.ActionTimeoutEntity;
import org.springframework.data.repository.CrudRepository;

public interface ActionTimeoutRepository extends CrudRepository<ActionTimeoutEntity, Long> {
    ActionTimeoutEntity findByActionId(long id);
}

