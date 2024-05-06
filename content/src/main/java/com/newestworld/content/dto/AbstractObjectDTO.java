package com.newestworld.content.dto;

import com.newestworld.commons.model.AbstractObject;
import com.newestworld.commons.model.ModelParameters;
import com.newestworld.content.model.entity.AbstractObjectEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AbstractObjectDTO implements AbstractObject {

    private long id;

    private long structureId;

    private String name;

    private ModelParameters parameters;

    private LocalDateTime createdAt;

    public AbstractObjectDTO(final AbstractObjectEntity entity, final ModelParameters parameters) {
        this.id = entity.getId();
        this.structureId = entity.getStructureId();
        this.name = entity.getName();
        this.parameters = parameters;
        this.createdAt = entity.getCreatedAt();
    }
}
