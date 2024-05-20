package com.newestworld.content.service;

import com.newestworld.commons.model.ActionStructure;
import com.newestworld.content.dao.ActionStructureRepository;
import com.newestworld.content.dto.ActionStructureCreateDTO;
import com.newestworld.content.dto.ActionStructureDTO;
import com.newestworld.content.model.entity.ActionStructureEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActionStructureService {

    private final ActionStructureRepository actionStructureRepository;

    public ActionStructure create(final ActionStructureCreateDTO request) {
        ActionStructureEntity entity = new ActionStructureEntity(request);
        actionStructureRepository.save(entity);
        log.info("ActionStructure with {} id created", entity.getId());
        return new ActionStructureDTO(entity);
    }

    public void delete(final long id) {
        actionStructureRepository.save(actionStructureRepository.mustFindByIdAndDeletedIsFalse(id).withDeleted(true));
        // todo: must delete all existing actions with this structureId
        log.info("ActionStructure with {} id deleted", id);
    }

    public ActionStructure findById(final long id) {
        return new ActionStructureDTO(
                actionStructureRepository.mustFindByIdAndDeletedIsFalse(id)
        );
    }

    public ActionStructure findByName(final String name)    {
        ActionStructureEntity entity = actionStructureRepository.mustFindByNameAndDeletedIsFalse(name);
        return new ActionStructureDTO(entity);
    }
}
