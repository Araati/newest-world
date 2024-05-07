package com.newestworld.content.dto;

import com.newestworld.commons.model.AbstractObjectStructure;
import com.newestworld.commons.model.StructureParameter;
import com.newestworld.content.model.entity.AbstractObjectStructureEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AbstractObjectStructureDTO implements AbstractObjectStructure {

    private long id;

    private String name;

    private List<StructureParameter> parameters;

    private LocalDateTime createdAt;

    public AbstractObjectStructureDTO(final AbstractObjectStructureEntity source, List<StructureParameter> parameters) {
        this.id = source.getId();
        this.name = source.getName();
        this.parameters = parameters;
        this.createdAt = source.getCreatedAt();
    }
}
