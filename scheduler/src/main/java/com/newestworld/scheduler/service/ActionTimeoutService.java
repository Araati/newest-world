package com.newestworld.scheduler.service;

import com.newestworld.commons.model.ActionTimeout;
import com.newestworld.commons.model.IdReference;
import com.newestworld.scheduler.dto.ActionTimeoutCreateDTO;
import com.newestworld.scheduler.dto.ActionTimeoutDTO;

import java.util.List;

public interface ActionTimeoutService {

    ActionTimeout create(final ActionTimeoutCreateDTO request);

    List<IdReference> findAll(final long time);

    void delete(final long actionId);
}
