package com.newestworld.content.dao;

import com.newestworld.content.model.entity.NodeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NodeRepository extends CrudRepository<NodeEntity, Long> {

    List<NodeEntity> findAllByStructureIdAndDeletedIsFalse(final long id);

}
