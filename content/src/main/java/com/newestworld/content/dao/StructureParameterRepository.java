package com.newestworld.content.dao;

import com.newestworld.content.model.entity.StructureParameterEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StructureParameterRepository extends CrudRepository<StructureParameterEntity, Long> {

    List<StructureParameterEntity> findAllByStructureIdAndDeletedIsFalse(final long id);
    List<StructureParameterEntity> findAllByStructureIdInAndDeletedIsFalse(final List<Long> ids);

}
