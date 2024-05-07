package com.newestworld.content.dto;

import com.newestworld.commons.model.StructureParameter;
import com.newestworld.content.model.entity.StructureParameterEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StructureParameterDTO implements StructureParameter {

    private String name;
    private boolean required;
    private String type;
    private String init;
    private Long min;
    private Long max;

    public StructureParameterDTO(final StructureParameterEntity source) {
        this.name = source.getName();
        this.required = source.isRequired();
        this.type = source.getType();
        this.init = source.getInit();
        this.min = source.getMin();
        this.max = source.getMax();
    }
}
