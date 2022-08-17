package com.newestworld.scheduler.service;

import com.newestworld.commons.model.IdReference;

import java.util.List;

public interface ActionTimeoutService {

    List<IdReference> findAll(long time);

    void delete(long actionId);
}
