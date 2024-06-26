package com.newestworld.scheduler.service.impl;

import com.newestworld.commons.model.ActionTimeout;
import com.newestworld.commons.model.IdReference;
import com.newestworld.scheduler.dao.ActionTimeoutRepository;
import com.newestworld.scheduler.dto.ActionDTO;
import com.newestworld.scheduler.dto.ActionTimeoutCreateDTO;
import com.newestworld.scheduler.dto.ActionTimeoutDTO;
import com.newestworld.scheduler.model.entity.ActionTimeoutEntity;
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
    public ActionTimeout create(final ActionTimeoutCreateDTO request) {
        ActionTimeoutEntity entity = new ActionTimeoutEntity(request);
        actionTimeoutRepository.save(entity);
        return new ActionTimeoutDTO(entity);
    }

    @Override
    public List<IdReference> findAll(final long time) {
        var entityList = actionTimeoutRepository.findAllByTimeoutLessThanAndDeletedIsFalseAndProcessingIsFalse(time);
        return entityList.stream().map(x -> new ActionDTO(x.getActionId())).collect(Collectors.toList());
    }

    @Override
    public void delete(final long actionId) {
        var entity = actionTimeoutRepository.mustFindByActionId(actionId);
        actionTimeoutRepository.save(entity.withDeleted(true));
    }

    public void markAllProcessing(final List<IdReference> list)    {
        actionTimeoutRepository.saveAll(actionTimeoutRepository
                .findAllByActionIdIn(list.stream().map(IdReference::getId).toList()).stream()
                .map(x -> x.withProcessing(true)).toList());
    }

}
