package com.newestworld.content.dao;

import com.newestworld.content.model.entity.BasicActionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BasicActionRepository extends CrudRepository<BasicActionEntity, Long> {

    List<BasicActionEntity> findAllByStructureIdAndDeletedIsFalse(final long id);

}
