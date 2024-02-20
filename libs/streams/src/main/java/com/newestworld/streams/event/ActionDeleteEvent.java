package com.newestworld.streams.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionDeleteEvent implements Event {

    private long actionId;

}
