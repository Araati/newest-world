package com.newestworld.content.dao;

import com.newestworld.content.model.entity.ActionParamsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ActionParamsRepository extends CrudRepository<ActionParamsEntity, Long> {

    List<ActionParamsEntity> findAllByActionIdAndDeletedIsFalse(final long id);

}
