package com.newestworld.content.dao;

import com.newestworld.content.model.entity.ModelParameterEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ModelParameterRepository extends CrudRepository<ModelParameterEntity, Long> {

    List<ModelParameterEntity> findAllByModelIdAndDeletedIsFalse(final long id);
    List<ModelParameterEntity> findAllByModelIdInAndDeletedIsFalse(final List<Long> ids);

}
