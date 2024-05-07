package com.newestworld.content.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.newestworld.commons.annotation.ValidStructureParameter;
import com.newestworld.commons.model.StructureParameter;
import com.newestworld.commons.validation.ClassChecks;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@GroupSequence({StructureParameterCreateDTO.class, ClassChecks.class})
@ValidStructureParameter(groups = ClassChecks.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StructureParameterCreateDTO implements StructureParameter {

    @NotBlank
    @JsonProperty(required = true)
    private String name;

    @JsonProperty(required = true)
    private boolean required;

    @NotBlank
    @JsonProperty(required = true)
    private String type;

    private String init;

    private Long min;

    private Long max;

}
