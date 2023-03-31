package com.newestworld.content.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.newestworld.streams.event.ActionCreateEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionCreateDTO {

    // TODO: 28.04.2022 @NotEmpty

    @JsonProperty(value = "type", required = true)
    private int type;

    @JsonProperty(value = "params")
    private List<ActionParamsCreateDTO> params;

    public ActionCreateDTO(ActionCreateEvent event) {
        this.type = event.getType();

        // TODO: 05.08.2022 Уродство.
        List<String> keys = event.getParams().keySet().stream().collect(Collectors.toList());
        List<String> values = event.getParams().values().stream().collect(Collectors.toList());
        List<ActionParamsCreateDTO> list = new ArrayList<>();
        for(int i = 0; keys.size() > i; i++)   {
           list.add(new ActionParamsCreateDTO(keys.get(i), values.get(i)));
        }
        this.params = list;
    }
}
