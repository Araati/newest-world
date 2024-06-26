package com.newestworld.streams.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionCreateEvent implements Event {

    private String name;

    private Map<String, String> input;

}
