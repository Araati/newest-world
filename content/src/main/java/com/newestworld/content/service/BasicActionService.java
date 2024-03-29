package com.newestworld.content.service;

import com.newestworld.commons.model.ActionParameters;
import com.newestworld.commons.model.BasicAction;
import com.newestworld.content.dao.BasicActionRepository;
import com.newestworld.content.dto.BasicActionCreateDTO;
import com.newestworld.content.dto.BasicActionDTO;
import com.newestworld.content.model.entity.BasicActionEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasicActionService {

    private final ActionParamsService actionParamsService;
    private final BasicActionRepository basicActionRepository;

    public List<BasicAction> createAll(final long actionId, final List<BasicActionCreateDTO> request) {
        List<BasicAction> basicActionDTOS = new ArrayList<>();
        for (BasicActionCreateDTO basicActionCreateDTO : request) {
            BasicActionEntity basicActionEntity = new BasicActionEntity(actionId, basicActionCreateDTO);
            basicActionRepository.save(basicActionEntity);
            ActionParameters actionParameters = actionParamsService.create(basicActionEntity.getId(), basicActionCreateDTO.getParams());
            basicActionDTOS.add(new BasicActionDTO(basicActionEntity, actionParameters));
        }
        return basicActionDTOS;
    }

    public void deleteAll(final long actionId) {
        List<BasicActionEntity> basicActionEntities = basicActionRepository.findAllByStructureIdAndDeletedIsFalse(actionId);
        for (BasicActionEntity basicActionEntity : basicActionEntities) {
            basicActionRepository.save(basicActionEntity.withDeleted(true));
            actionParamsService.delete(actionId);
        }
    }

    public List<BasicAction> findAllById(final long actionId)  {
        List<BasicActionEntity> basicActionEntities = basicActionRepository.findAllByStructureIdAndDeletedIsFalse(actionId);
        List<BasicAction> basicActions = new ArrayList<>();
        for (BasicActionEntity basicActionEntity : basicActionEntities) {
            basicActions.add(new BasicActionDTO(basicActionEntity, actionParamsService.findById(basicActionEntity.getId())));
        }
        return basicActions;
    }
}
