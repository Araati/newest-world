package com.newestworld.scheduler.service.impl;

import com.newestworld.commons.model.IdReference;
import com.newestworld.scheduler.dao.ActionTimeoutRepository;
import com.newestworld.scheduler.dto.ActionDTO;
import com.newestworld.scheduler.service.ActionTimeoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultActionTimeoutService implements ActionTimeoutService {

    private final ActionTimeoutRepository actionTimeoutRepository;

    @Override
    public List<IdReference> findAll(final long time) {
        var entityList = actionTimeoutRepository.findAllByTimeoutLessThanAndDeletedIsFalse(time);
        return entityList.stream().map(x -> new ActionDTO(x.getActionId())).collect(Collectors.toList());
    }

    @Override
    public void delete(final long actionId) {
        var entity = actionTimeoutRepository.mustFindById(actionId);
        actionTimeoutRepository.save(entity.withDeleted(true));
    }

}
