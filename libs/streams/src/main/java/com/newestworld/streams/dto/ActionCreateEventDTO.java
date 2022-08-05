package com.newestworld.streams.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionCreateEventDTO {

    private int type;

    private HashMap<String, String> params;

}
