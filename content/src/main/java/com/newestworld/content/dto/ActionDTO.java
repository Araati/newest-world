package com.newestworld.content.dto;

import com.newestworld.commons.model.Action;
import com.newestworld.content.model.entity.ActionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionDTO implements Action {

    private long id;

    private long structureId;

    private String name;

    private long timeout;

    private Map<String, String> parameters;

    private LocalDateTime createdAt;

    public ActionDTO(final ActionEntity source, final long timeout)    {
        this.id = source.getId();
        this.structureId = source.getStructureId();
        this.name = source.getName();
        this.timeout = timeout;
        this.parameters = source.getParameters();
        this.createdAt = source.getCreatedAt();
    }

}
