package com.newestworld.streams.event.batch;

import com.newestworld.streams.event.ActionDataRequestEvent;
import com.newestworld.streams.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionDataRequestBatchEvent implements Event {

    private Collection<ActionDataRequestEvent> batch = new ArrayList<>();

    public ActionDataRequestBatchEvent(final ActionTimeoutBatchEvent source) {
        this.batch = source.getBatch().stream().map(ActionDataRequestEvent::new).toList();
    }

    public int getSize()    {
        return batch.size();
    }

}
