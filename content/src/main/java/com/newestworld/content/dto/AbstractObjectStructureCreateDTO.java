package com.newestworld.content.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AbstractObjectStructureCreateDTO {

    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonProperty(value = "properties", required = true)
    Map<String, String> properties;

}
