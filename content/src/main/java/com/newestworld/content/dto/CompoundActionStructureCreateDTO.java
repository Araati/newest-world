package com.newestworld.content.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompoundActionStructureCreateDTO {

    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonProperty(value = "input")
    private List<String> input;

    @JsonProperty(value = "steps", required = true)
    private List<BasicActionCreateDTO> steps;

}
