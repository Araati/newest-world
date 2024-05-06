package com.newestworld.commons.model;

import com.newestworld.commons.annotation.ValidModelParameter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ValidModelParameter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModelParameter {

    private long modelId;
    private String name;
    private boolean required;
    private String value;
    private String type;
    private String init;
    private Long min;
    private Long max;


}
