package com.worldcalls.content.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionParamsCreateDTO {

    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonProperty(value = "value", required = true)
    private String value;

}
