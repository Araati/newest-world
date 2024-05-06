package com.newestworld.content.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModelParameterCreateDTO {

    @JsonProperty(required = true)
    private String name;

    @JsonProperty(required = true)
    private boolean required;

    private String data;

    @JsonProperty(required = true)
    private String type;

    private String init;

    private Long min;

    private Long max;

}
