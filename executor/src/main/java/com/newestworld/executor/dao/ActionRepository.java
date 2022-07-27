package com.newestworld.executor.dao;

import com.newestworld.executor.model.entity.ActionEntity;
import org.springframework.data.repository.CrudRepository;

public interface ActionRepository extends CrudRepository<ActionEntity, Long> {
}
