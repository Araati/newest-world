package com.newestworld.content.dao;

import com.newestworld.commons.exception.ResourceNotFoundException;
import com.newestworld.content.model.entity.CompoundActionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CompoundActionRepository extends CrudRepository<CompoundActionEntity, Long> {

    Optional<CompoundActionEntity> findByIdAndDeletedIsFalse(final long id);
    default CompoundActionEntity mustFindByIdAndDeletedIsFalse(final long id)   {
        return findByIdAndDeletedIsFalse(id).orElseThrow(() -> new ResourceNotFoundException("CompoundAction", id));
    }

    default CompoundActionEntity mustFindById(final long id)    {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("CompoundAction", id));
    }

}
