package com.newestworld.commons.model;

import com.newestworld.commons.annotation.ValidModelParameter;
import com.newestworld.commons.validation.ClassChecks;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@GroupSequence({ModelParameter.class, ClassChecks.class})
@ValidModelParameter(groups = ClassChecks.class)
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModelParameter {

    @NotBlank
    private String name;

    private boolean required;
    private String data;

    @NotBlank
    private String type;

    private Long min;
    private Long max;


}
