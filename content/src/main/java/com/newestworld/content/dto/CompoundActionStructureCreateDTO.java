package com.newestworld.content.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.newestworld.commons.model.ModelParameters;
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

    private ModelParameters parameters;

    @JsonProperty(value = "steps", required = true)
    private List<NodeCreateDTO> steps;

}
