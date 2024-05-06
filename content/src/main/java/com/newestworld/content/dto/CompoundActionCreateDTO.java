package com.newestworld.content.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.newestworld.streams.event.CompoundActionCreateEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompoundActionCreateDTO {

    @JsonProperty(value = "name", required = true)
    private String name;

    private Map<String, String> input;

}
