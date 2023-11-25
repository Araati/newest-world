package com.newestworld.content.dao;

import com.newestworld.commons.exception.ResourceNotFoundException;
import com.newestworld.content.model.entity.ActionParamsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ActionParamsRepository extends CrudRepository<ActionParamsEntity, Long> {

    List<ActionParamsEntity> findAllByActionIdAndDeletedIsFalse(final long id);

}
