package com.newestworld.commons.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StructureProperty {

    @JsonProperty(required = true)
    private String name;

    @JsonProperty(required = true)
    private boolean required;

    private String init;
    private Long min;
    private Long max;

}
