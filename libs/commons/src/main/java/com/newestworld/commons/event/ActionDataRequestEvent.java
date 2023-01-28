package com.newestworld.commons.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionDataRequestEvent {

    private long id;

    public ActionDataRequestEvent(ActionTimeoutEvent actionTimeoutEvent) {
        this.id = actionTimeoutEvent.getId();
    }
}
