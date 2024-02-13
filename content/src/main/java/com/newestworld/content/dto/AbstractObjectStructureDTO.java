package com.newestworld.content.dto;

import com.newestworld.commons.model.AbstractObjectStructure;
import com.newestworld.content.model.entity.AbstractObjectStructureEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AbstractObjectStructureDTO implements AbstractObjectStructure {

    private long id;

    private String name;

    private Map<String, String> properties;

    private LocalDateTime createdAt;

    public AbstractObjectStructureDTO(final AbstractObjectStructureEntity source) {
        this.id = source.getId();
        this.name = source.getName();
        this.properties = source.getProperties();
        this.createdAt = source.getCreatedAt();
    }
}
