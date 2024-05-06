package com.newestworld.content.service;

import com.newestworld.commons.model.BasicAction;
import com.newestworld.commons.model.CompoundActionStructure;
import com.newestworld.commons.model.ModelParameters;
import com.newestworld.content.dao.CompoundActionStructureRepository;
import com.newestworld.content.dto.CompoundActionStructureCreateDTO;
import com.newestworld.content.dto.CompoundActionStructureDTO;
import com.newestworld.content.model.entity.CompoundActionStructureEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompoundActionStructureService {

    private final BasicActionService basicActionService;
    private final ModelParameterService modelParameterService;
    private final CompoundActionStructureRepository compoundActionStructureRepository;

    public CompoundActionStructure create(final CompoundActionStructureCreateDTO request) {
        CompoundActionStructureEntity compoundActionStructureEntity = new CompoundActionStructureEntity(request);
        compoundActionStructureRepository.save(compoundActionStructureEntity);
        List<BasicAction> basicActionDTOS = basicActionService.createAll(compoundActionStructureEntity.getId(), request.getSteps());
        log.info("CompoundActionStructure with {} id created", compoundActionStructureEntity.getId());
        return new CompoundActionStructureDTO(compoundActionStructureEntity, request.getParameters(), basicActionDTOS);
    }

    public void delete(final long id) {
        compoundActionStructureRepository.save(compoundActionStructureRepository.mustFindByIdAndDeletedIsFalse(id).withDeleted(true));
        basicActionService.deleteAll(id);
        // todo: must delete all existing compound with this structureId
        log.info("CompoundActionStructure with {} id deleted", id);
    }

    public CompoundActionStructure findById(final long id) {
        ModelParameters parameters = modelParameterService.findById(id);
        return new CompoundActionStructureDTO(
                compoundActionStructureRepository.mustFindByIdAndDeletedIsFalse(id),
                parameters,
                basicActionService.findAllById(id)
        );
    }

    public CompoundActionStructure findByName(final String name)    {
        CompoundActionStructureEntity entity = compoundActionStructureRepository.mustFindByNameAndDeletedIsFalse(name);
        ModelParameters parameters = modelParameterService.findById(entity.getId());
        return new CompoundActionStructureDTO(
                entity,
                parameters,
                basicActionService.findAllById(entity.getId())
        );
    }
}
