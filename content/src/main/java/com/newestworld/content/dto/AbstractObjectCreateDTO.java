package com.newestworld.content.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.newestworld.streams.event.AbstractObjectCreateEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class AbstractObjectCreateDTO {

    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonProperty(value = "properties", required = true)
    private Map<String, String> properties;

    public AbstractObjectCreateDTO(final AbstractObjectCreateEvent event) {
        this.name = event.getName();
        this.properties = event.getProperties();
    }
}
