package com.newestworld.streams.event;

import com.newestworld.commons.model.ActionParameters;
import com.newestworld.commons.model.ActionType;
import com.newestworld.commons.model.BasicAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasicActionEvent {

    private long id;

    // Енамка не передается нормально, разбираться мне лень
    private int type;

    private ActionParameters parameters;

    private LocalDateTime createdAt;

    public BasicActionEvent(final BasicAction basicAction) {
        this.id = basicAction.getId();
        this.type = basicAction.getType().getId();
        this.parameters = basicAction.getParameters();
        //this.createdAt = basicAction.getCreatedAt();
    }
}
