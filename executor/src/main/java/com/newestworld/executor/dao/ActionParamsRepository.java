package com.newestworld.executor.dao;

import com.newestworld.executor.model.entity.ActionParamsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ActionParamsRepository extends CrudRepository<ActionParamsEntity, Long> {

    List<ActionParamsEntity> findAllByActionId(final long id);
    List<ActionParamsEntity> findAllByActionIdAndDeletedIsFalse(final long id);
}
