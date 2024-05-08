package com.newestworld.content.dao;

import com.newestworld.commons.exception.ResourceNotFoundException;
import com.newestworld.content.model.entity.AbstractObjectEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AbstractObjectRepository extends CrudRepository<AbstractObjectEntity, Long> {

    Optional<AbstractObjectEntity> findByIdAndDeletedIsFalse(final long id);
    default AbstractObjectEntity mustFindByIdAndDeletedIsFalse(final long id)   {
        return findByIdAndDeletedIsFalse(id).orElseThrow(() -> new ResourceNotFoundException("AbstractObject", id));
    }

    List<AbstractObjectEntity> findAllByStructureIdAndDeletedIsFalse(final long structureId);

}
