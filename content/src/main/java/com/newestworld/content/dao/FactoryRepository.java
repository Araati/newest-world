package com.newestworld.content.dao;

import com.newestworld.commons.exception.ResourceNotFoundException;
import com.newestworld.content.model.entity.FactoryEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FactoryRepository extends CrudRepository<FactoryEntity, Long> {

    Optional<FactoryEntity> findByIdAndDeletedIsFalse(final long id);

    default FactoryEntity mustFindByIdAndDeletedIsFalse(final long id)  {
        return findByIdAndDeletedIsFalse(id).orElseThrow(() -> new ResourceNotFoundException("Factory", id));
    }

    default FactoryEntity mustFindById(final long id)   {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("Factory", id));
    }

}
