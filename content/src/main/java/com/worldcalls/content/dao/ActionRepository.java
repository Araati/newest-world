package com.worldcalls.content.dao;

import com.worldcalls.content.dto.Action;
import com.worldcalls.content.model.entity.ActionEntity;
import org.springframework.data.repository.CrudRepository;

public interface ActionRepository extends CrudRepository<ActionEntity, Long> {
}
