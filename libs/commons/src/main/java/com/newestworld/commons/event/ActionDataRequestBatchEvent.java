package com.newestworld.commons.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionDataRequestBatchEvent {

    private Collection<ActionDataRequestEvent> batch = new ArrayList<>();

    public ActionDataRequestBatchEvent(ActionTimeoutBatchEvent source) {
        this.batch = source.getBatch().stream().map(ActionDataRequestEvent::new).collect(Collectors.toList());
    }

    public int getSize()    {
        return batch.size();
    }

}
