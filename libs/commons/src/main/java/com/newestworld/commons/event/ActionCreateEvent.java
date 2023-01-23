package com.newestworld.commons.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionCreateEvent {

    private int type;

    private HashMap<String, String> params;

}