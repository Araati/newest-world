package com.newestworld.content.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NodeCreateDTO {

    @JsonProperty(value = "type", required = true)
    private int type;

    @JsonProperty(value = "position", required = true)
    private Long position;

    private Map<String, String> parameters;

}
