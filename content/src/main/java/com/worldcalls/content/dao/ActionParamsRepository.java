package com.worldcalls.content.dao;

import com.worldcalls.content.dto.ActionParams;
import com.worldcalls.content.model.entity.ActionParamsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ActionParamsRepository extends CrudRepository<ActionParamsEntity, Long> {
    ActionParamsEntity findByActionId(long id);
    List<ActionParamsEntity> findAllByActionId(long id);
}
