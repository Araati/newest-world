package com.worldcalls.content.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionCreateDTO {

    // TODO: 28.04.2022 @NotEmpty

    @JsonProperty(value = "type", required = true)
    private int type;

    @JsonProperty(value = "params")
    private List<ActionParamsCreateDTO> params;

}
