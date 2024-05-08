package com.newestworld.content.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.newestworld.streams.event.AbstractObjectCreateEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AbstractObjectCreateDTO {

    @JsonProperty(required = true)
    private String name;

    @JsonProperty(required = true)
    private Map<String, String> input;

    public AbstractObjectCreateDTO(final AbstractObjectCreateEvent event) {
        this.name = event.getName();
        this.input = event.getProperties();
    }
}
