package com.newestworld.content.service;

import com.newestworld.commons.model.ActionParameter;
import com.newestworld.commons.model.ActionParameters;
import com.newestworld.commons.model.BasicAction;
import com.newestworld.commons.model.CompoundActionStructure;
import com.newestworld.content.dao.ActionParamsRepository;
import com.newestworld.content.dao.BasicActionRepository;
import com.newestworld.content.dao.CompoundActionStructureRepository;
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

        List<Long> basicActionEntityIdsList = new ArrayList<>();
        List<BasicAction> basicActionDTOS = new ArrayList<>();
        for (int i = 0; i < request.getSteps().size(); i++) {
            BasicActionEntity basicActionEntity = new BasicActionEntity(compoundActionStructureEntity.getId(), request.getSteps().get(i));
            basicActionRepository.save(basicActionEntity);
            basicActionEntityIdsList.add(basicActionEntity.getId());
            List<ActionParameter> actionParameters = new ArrayList<>();
            for (int j = 0; j < request.getSteps().get(i).getParams().size(); j++) {
                ActionParamsEntity actionParamsEntity = new ActionParamsEntity(basicActionEntity.getId(), request.getSteps().get(i).getParams().get(j));
                actionParamsRepository.save(actionParamsEntity);
                actionParameters.add(new ActionParameter(actionParamsEntity.getActionId(), actionParamsEntity.getName(), actionParamsEntity.getValue()));
            }
            basicActionDTOS.add(new BasicActionDTO(basicActionEntity, new ActionParameters.Impl(actionParameters)));
        }

        return new CompoundActionStructureDTO(compoundActionStructureEntity, basicActionDTOS);
    }

    public void delete(final long id) {

        compoundActionStructureRepository.save(compoundActionStructureRepository.mustFindById(id).withDeleted(true));

        List<BasicActionEntity> basicActionEntities = basicActionRepository.findAllByActionIdAndDeletedIsFalse(id);

        for (int i = 0; i < basicActionEntities.size(); i++)   {
            basicActionRepository.save(basicActionEntities.get(i).withDeleted(true));
            actionParamsRepository.saveAll(actionParamsRepository.findAllByActionId(basicActionEntities.get(i).getId()).stream().map(x -> x.withDeleted(true)).collect(Collectors.toList()));
        }

        log.info("CompoundActionStructure with {} id deleted", id);

    }

    public CompoundActionStructure findById(final long id) {

        CompoundActionStructureEntity entity = compoundActionStructureRepository.mustFindByIdAndDeletedIsFalse(id);
        List<BasicActionEntity> basicActionEntities = basicActionRepository.findAllByActionId(id);
        List<BasicAction> basicActions = new ArrayList<>();

        for (int i = 0; i < basicActionEntities.size(); i++) {
            List<ActionParamsEntity> actionParamsEntities = actionParamsRepository.findAllByActionId(id);
            List<ActionParameter> actionParameterList = new ArrayList<>();
            for (int j = 0; j < actionParamsEntities.size(); j++) {
                ActionParamsEntity source = actionParamsEntities.get(j);
                actionParameterList.add(new ActionParameter(source.getActionId(), source.getName(), source.getValue()));
            }
            ActionParameters actionParameters = new ActionParameters.Impl(actionParameterList);
            basicActions.add(new BasicActionDTO(basicActionEntities.get(i), actionParameters));
        }

        return new CompoundActionStructureDTO(entity, basicActions);
    }

}
