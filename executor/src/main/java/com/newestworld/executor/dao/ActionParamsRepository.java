package com.newestworld.executor.dao;

import com.newestworld.executor.model.entity.ActionParamsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ActionParamsRepository extends CrudRepository<ActionParamsEntity, Long> {

    ActionParamsEntity findByActionId(long id);
    Optional<List<ActionParamsEntity>> findAllByActionId(long id);
}
