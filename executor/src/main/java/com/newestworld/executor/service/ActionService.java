package com.newestworld.executor.service;

import com.newestworld.commons.exception.ResourceNotFoundException;
import com.newestworld.commons.model.Action;
import com.newestworld.commons.model.ActionParameters;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ActionService {

    List<Action> findAllByIds(final Collection<Long> ids);

    Optional<Action> findById(final long id);

    default Action mustFindById(final long id) {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("Action", id));
    }

    ActionParameters findAllParamsByActionId(final long id);
}
