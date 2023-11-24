package com.newestworld.content.dto;

import com.newestworld.commons.model.ActionParameters;
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

    private long timeout;

    private ActionParameters input;

    private boolean inProgress;

    private LocalDateTime createdAt;

    public CompoundActionDTO(final CompoundActionEntity source, final ActionParameters source1, final long timeout)    {
        this.id = source.getId();
        this.name = source.getName();
        this.timeout = timeout;
        this.input = source1;
        this.inProgress = source.isInProgress();
        this.createdAt = source.getCreatedAt();
    }

}
