package com.newestworld.streams.event.batch;

import com.newestworld.streams.event.ActionDataEvent;
import com.newestworld.streams.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionDataBatchEvent implements Event {

    private Collection<ActionDataEvent> batch = new ArrayList<>();

    public int getSize()    {
        return batch.size();
    }

}
