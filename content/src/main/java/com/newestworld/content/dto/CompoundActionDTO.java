package com.newestworld.content.dto;

import com.newestworld.commons.model.ModelParameters;
import com.newestworld.commons.model.CompoundAction;
import com.newestworld.content.model.entity.CompoundActionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompoundActionDTO implements CompoundAction {

    private long id;

    private String name;

    private long structureId;

    private long timeout;

    private ModelParameters parameters;

    private LocalDateTime createdAt;

    public CompoundActionDTO(final CompoundActionEntity source, final ModelParameters source1, final long timeout)    {
        this.id = source.getId();
        this.name = source.getName();
        this.structureId = source.getStructureId();
        this.timeout = timeout;
        this.parameters = source1;
        this.createdAt = source.getCreatedAt();
    }

}
