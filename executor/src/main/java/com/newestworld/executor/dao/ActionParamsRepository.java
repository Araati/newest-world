package com.newestworld.executor.dao;

import com.newestworld.executor.model.entity.ActionParamsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ActionParamsRepository extends CrudRepository<ActionParamsEntity, Long> {

    ActionParamsEntity findByActionId(long id);
    List<ActionParamsEntity> findAllByActionId(long id);
}
