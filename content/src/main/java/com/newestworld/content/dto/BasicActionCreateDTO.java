package com.newestworld.content.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasicActionCreateDTO {

    @JsonProperty(value = "type", required = true)
    private int type;

    @JsonProperty(value = "localPosition", required = true)
    private Long localPosition;

    @JsonProperty(value = "params")
    private List<ModelParameterCreateDTO> params;

}
