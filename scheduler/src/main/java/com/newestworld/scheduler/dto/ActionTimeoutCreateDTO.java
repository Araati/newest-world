package com.newestworld.scheduler.dto;

import com.newestworld.streams.event.ActionTimeoutCreateEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionTimeoutCreateDTO {

    private long actionId;
    private long timeout;

    public ActionTimeoutCreateDTO(final ActionTimeoutCreateEvent event) {
        this.actionId = event.getActionId();
        this.timeout = event.getTimeout();
    }
}
