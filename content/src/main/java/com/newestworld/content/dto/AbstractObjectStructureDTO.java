package com.newestworld.content.dto;

import com.newestworld.commons.model.AbstractObjectStructure;
import com.newestworld.commons.model.StructureParameter;
import com.newestworld.content.model.entity.AbstractObjectStructureEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AbstractObjectStructureDTO implements AbstractObjectStructure {

    private long id;

    private String name;

    private List<StructureParameter> parameters;

    private LocalDateTime createdAt;

    public AbstractObjectStructureDTO(final AbstractObjectStructureEntity source) {
        this.id = source.getId();
        this.name = source.getName();
        this.parameters = new ArrayList<>(source.getParameters().stream().map(StructureParameterDTO::new).toList());
        this.createdAt = source.getCreatedAt();
    }
}
