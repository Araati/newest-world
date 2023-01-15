package com.newestworld.scheduler.service;

import com.newestworld.commons.model.IdReference;

import java.util.List;

public interface ActionTimeoutService {

    List<IdReference> findAll(final long time);

    void delete(final long actionId);
}
