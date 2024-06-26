package com.newestworld.streams.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionDataRequestEvent implements Event{

    private long id;

    public ActionDataRequestEvent(final ActionTimeoutEvent actionTimeoutEvent) {
        this.id = actionTimeoutEvent.getId();
    }
}
