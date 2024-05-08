package com.newestworld.content.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionStructureCreateDTO {

    @JsonProperty(required = true)
    private String name;

    @Valid
    @JsonProperty(required = true)
    private List<StructureParameterCreateDTO> parameters;

    @JsonProperty(required = true)
    private List<NodeCreateDTO> steps;

}
