package com.newestworld.scheduler.dto;

import com.newestworld.commons.model.ActionTimeout;
import com.newestworld.scheduler.model.entity.ActionTimeoutEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionTimeoutDTO implements ActionTimeout {

    private long id;
    private long actionId;
    private long timeout;

    public ActionTimeoutDTO(final ActionTimeoutEntity entity) {
        this.id = entity.getId();
        this.actionId = entity.getActionId();
        this.timeout = entity.getTimeout();
    }
}
