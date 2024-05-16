package com.newestworld.commons.model;

import com.newestworld.commons.annotation.ValidModelParameter;
import com.newestworld.commons.validation.ClassChecks;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@GroupSequence({ModelParameter.class, ClassChecks.class})
@ValidModelParameter(groups = ClassChecks.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModelParameter {

    @NotBlank
    private String name;

    private boolean required;

    @With
    private String data;

    @NotBlank
    private String type;

    private Long max;
    private Long min;


}
