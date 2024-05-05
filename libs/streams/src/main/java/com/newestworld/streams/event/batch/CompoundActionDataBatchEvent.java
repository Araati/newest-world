package com.newestworld.streams.event.batch;

import com.newestworld.streams.event.CompoundActionDataEvent;
import com.newestworld.streams.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompoundActionDataBatchEvent implements Event {

    private Collection<CompoundActionDataEvent> batch = new ArrayList<>();

    public int getSize()    {
        return batch.size();
    }

}
