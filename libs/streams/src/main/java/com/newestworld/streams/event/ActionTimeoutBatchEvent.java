package com.newestworld.streams.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionTimeoutBatchEvent {

    private Collection<ActionTimeoutEvent> batch = new ArrayList<>();

    public int getSize() {
        return batch.size();
    }
}
