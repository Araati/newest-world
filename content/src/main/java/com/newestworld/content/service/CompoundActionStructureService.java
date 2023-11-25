package com.newestworld.content.service;

import com.newestworld.commons.model.ActionParameter;
import com.newestworld.commons.model.ActionParameters;
import com.newestworld.commons.model.BasicAction;
import com.newestworld.commons.model.CompoundActionStructure;
import com.newestworld.content.dao.ActionParamsRepository;
import com.newestworld.content.dao.BasicActionRepository;
import com.newestworld.content.dao.CompoundActionStructureRepository;
import com.newestworld.content.dto.ActionParamsCreateDTO;
import com.newestworld.content.dto.BasicActionDTO;
import com.newestworld.content.dto.CompoundActionStructureCreateDTO;
import com.newestworld.content.dto.CompoundActionStructureDTO;
import com.newestworld.content.model.entity.ActionParamsEntity;
import com.newestworld.content.model.entity.BasicActionEntity;
import com.newestworld.content.model.entity.CompoundActionStructureEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompoundActionStructureService {

    private final CompoundActionStructureRepository compoundActionStructureRepository;
    private final BasicActionRepository basicActionRepository;
    private final ActionParamsRepository actionParamsRepository;

    public CompoundActionStructure create(final CompoundActionStructureCreateDTO request) {
        CompoundActionStructureEntity compoundActionStructureEntity = new CompoundActionStructureEntity(request);
        compoundActionStructureRepository.save(compoundActionStructureEntity);

        List<BasicAction> basicActionDTOS = new ArrayList<>();

        // TODO: 25.11.2023 Extract to BasicActionService
        for (int i = 0; i < request.getSteps().size(); i++) {
            BasicActionEntity basicActionEntity = new BasicActionEntity(compoundActionStructureEntity.getId(), request.getSteps().get(i));
            basicActionRepository.save(basicActionEntity);

            List<ActionParameter> actionParameters = new ArrayList<>();
            List<ActionParamsCreateDTO> actionParamsCreateDTOS = request.getSteps().get(i).getParams();
            for (ActionParamsCreateDTO actionParamsCreateDTO : actionParamsCreateDTOS) {
                ActionParamsEntity actionParamsEntity = new ActionParamsEntity(basicActionEntity.getId(), actionParamsCreateDTO);
                actionParamsRepository.save(actionParamsEntity);
                actionParameters.add(new ActionParameter(actionParamsEntity.getActionId(), actionParamsEntity.getName(), actionParamsEntity.getValue()));
            }

            basicActionDTOS.add(new BasicActionDTO(basicActionEntity, new ActionParameters.Impl(actionParameters)));
        }

        log.info("CompoundActionStructure with {} id created", compoundActionStructureEntity.getId());
        return new CompoundActionStructureDTO(compoundActionStructureEntity, basicActionDTOS);
    }

    public void delete(final long id) {

        compoundActionStructureRepository.save(compoundActionStructureRepository.mustFindByIdAndDeletedIsFalse(id).withDeleted(true));

        // TODO: 25.11.2023 Extract to BasicActionService
        List<BasicActionEntity> basicActionEntities = basicActionRepository.findAllByActionIdAndDeletedIsFalse(id);
        for (BasicActionEntity basicActionEntity : basicActionEntities) {
            basicActionRepository.save(basicActionEntity.withDeleted(true));
            actionParamsRepository.saveAll(actionParamsRepository.findAllByActionIdAndDeletedIsFalse(basicActionEntity.getId()).stream().map(x -> x.withDeleted(true)).collect(Collectors.toList()));
        }

        log.info("CompoundActionStructure with {} id deleted", id);
    }

    public CompoundActionStructure findById(final long id) {

        CompoundActionStructureEntity entity = compoundActionStructureRepository.mustFindByIdAndDeletedIsFalse(id);

        // TODO: 25.11.2023 Extract to BasicActionService
        List<BasicActionEntity> basicActionEntities = basicActionRepository.findAllByActionIdAndDeletedIsFalse(id);
        List<BasicAction> basicActions = new ArrayList<>();
        for (BasicActionEntity basicActionEntity : basicActionEntities) {
            List<ActionParamsEntity> actionParamsEntities = actionParamsRepository.findAllByActionIdAndDeletedIsFalse(id);
            List<ActionParameter> actionParameterList = new ArrayList<>();
            for (ActionParamsEntity source : actionParamsEntities) {
                actionParameterList.add(new ActionParameter(source.getActionId(), source.getName(), source.getValue()));
            }
            ActionParameters actionParameters = new ActionParameters.Impl(actionParameterList);
            basicActions.add(new BasicActionDTO(basicActionEntity, actionParameters));
        }

        return new CompoundActionStructureDTO(entity, basicActions);
    }
}
