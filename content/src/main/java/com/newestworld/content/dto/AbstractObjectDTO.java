package com.newestworld.content.dto;

import com.newestworld.commons.model.AbstractObject;
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

    private Map<String, String> properties;

    private LocalDateTime createdAt;

    public AbstractObjectDTO(final AbstractObjectEntity entity) {
        this.id = entity.getId();
        this.structureId = entity.getStructureId();
        this.name = entity.getName();
        this.properties = entity.getProperties();
        this.createdAt = entity.getCreatedAt();
    }
}
