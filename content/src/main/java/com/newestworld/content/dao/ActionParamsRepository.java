package com.newestworld.content.dao;

import com.newestworld.commons.exception.ResourceNotFoundException;
import com.newestworld.content.model.entity.ActionParamsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ActionParamsRepository extends CrudRepository<ActionParamsEntity, Long> {
    Optional<ActionParamsEntity> findByActionId(final long id);
    default ActionParamsEntity mustFindByActionId(final long id)    {
        return findByActionId(id).orElseThrow(() -> new ResourceNotFoundException("ActionParam", id));
    }

    List<ActionParamsEntity> findAllByActionId(final long id);

    Optional<ActionParamsEntity> findByActionIdAndDeletedIsFalse(final long id);
    default ActionParamsEntity mustFindByActionIdAndDeletedIsFalse(final long id)  {
        return findByActionIdAndDeletedIsFalse(id).orElseThrow(() -> new ResourceNotFoundException("Action", id));
    }

    List<ActionParamsEntity> findAllByActionIdAndDeletedIsFalse(final long id);

}
