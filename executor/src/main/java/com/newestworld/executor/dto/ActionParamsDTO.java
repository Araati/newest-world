package com.newestworld.executor.dto;

import com.newestworld.commons.dto.ActionParams;
import com.newestworld.executor.model.entity.ActionParamsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionParamsDTO implements ActionParams {

    private String name;

    private String value;

    public ActionParamsDTO(ActionParamsEntity source) {
        this.name = source.getName();
        this.value = source.getValue();
    }
}
