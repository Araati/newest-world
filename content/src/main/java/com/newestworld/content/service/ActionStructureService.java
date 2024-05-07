package com.newestworld.content.service;

import com.newestworld.commons.model.Node;
import com.newestworld.commons.model.ActionStructure;
import com.newestworld.commons.model.StructureParameter;
import com.newestworld.content.dao.ActionStructureRepository;
import com.newestworld.content.dto.ActionStructureCreateDTO;
import com.newestworld.content.dto.ActionStructureDTO;
import com.newestworld.content.model.entity.ActionStructureEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActionStructureService {

    private final NodeService nodeService;
    private final StructureParameterService structureParameterService;
    private final ActionStructureRepository actionStructureRepository;

    public ActionStructure create(final ActionStructureCreateDTO request) {
        ActionStructureEntity actionStructureEntity = new ActionStructureEntity(request);
        actionStructureRepository.save(actionStructureEntity);
        List<StructureParameter> parameters = new ArrayList<>(structureParameterService.create(actionStructureEntity.getId(), request.getParameters()));
        List<Node> nodeDTOS = nodeService.createAll(actionStructureEntity.getId(), request.getSteps());
        log.info("ActionStructure with {} id created", actionStructureEntity.getId());
        return new ActionStructureDTO(actionStructureEntity, parameters, nodeDTOS);
    }

    public void delete(final long id) {
        actionStructureRepository.save(actionStructureRepository.mustFindByIdAndDeletedIsFalse(id).withDeleted(true));
        nodeService.deleteAll(id);
        // todo: must delete all existing actions with this structureId
        log.info("ActionStructure with {} id deleted", id);
    }

    public ActionStructure findById(final long id) {
        List<StructureParameter> parameters = structureParameterService.findById(id);
        return new ActionStructureDTO(
                actionStructureRepository.mustFindByIdAndDeletedIsFalse(id),
                parameters,
                nodeService.findAllById(id)
        );
    }

    public ActionStructure findByName(final String name)    {
        ActionStructureEntity entity = actionStructureRepository.mustFindByNameAndDeletedIsFalse(name);
        List<StructureParameter> parameters = structureParameterService.findById(entity.getId());
        return new ActionStructureDTO(
                entity,
                parameters,
                nodeService.findAllById(entity.getId())
        );
    }
}
