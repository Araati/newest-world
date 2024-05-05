package com.newestworld.streams.event.batch;

import com.newestworld.streams.event.ActionTimeoutEvent;
import com.newestworld.streams.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionTimeoutBatchEvent implements Event {

    private Collection<ActionTimeoutEvent> batch = new ArrayList<>();

    public int getSize() {
        return batch.size();
    }
}
