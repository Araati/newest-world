package com.newestworld.content.dao;

import com.newestworld.commons.exception.ResourceNotFoundException;
import com.newestworld.content.model.entity.CompoundActionStructureEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CompoundActionStructureRepository extends CrudRepository<CompoundActionStructureEntity, Long> {

    Optional<CompoundActionStructureEntity> findByIdAndDeletedIsFalse(final long id);
    default CompoundActionStructureEntity mustFindByIdAndDeletedIsFalse(final long id)   {
        return findByIdAndDeletedIsFalse(id).orElseThrow(() -> new ResourceNotFoundException("CompoundActionStructure", id));
    }

    default CompoundActionStructureEntity mustFindById(final long id)    {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("CompoundActionStructure", id));
    }

}
