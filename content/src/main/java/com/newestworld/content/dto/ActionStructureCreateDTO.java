package com.newestworld.content.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.newestworld.commons.model.ModelParameter;
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
    private List<ModelParameter> parameters;

    @JsonProperty(required = true)
    private List<NodeCreateDTO> steps;

}
