package com.newestworld.executor.dto;

import com.newestworld.commons.model.ActionParameters;
import com.newestworld.commons.model.ActionType;
import com.newestworld.commons.model.BasicAction;
import com.newestworld.streams.event.BasicActionEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasicActionDTO implements BasicAction {

    private long id;

    private ActionType type;

    private ActionParameters parameters;

    private LocalDateTime createdAt;

    public BasicActionDTO(final BasicActionEvent basicActionEvent) {
        this.id = basicActionEvent.getId();
        this.type = ActionType.decode(basicActionEvent.getType());
        this.parameters = basicActionEvent.getParameters();
        this.createdAt = basicActionEvent.getCreatedAt();
    }
}