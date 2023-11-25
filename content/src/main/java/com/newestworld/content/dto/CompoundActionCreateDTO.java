package com.newestworld.content.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.newestworld.streams.event.CompoundActionCreateEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompoundActionCreateDTO {

    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonProperty(value = "input")
    private List<ActionParamsCreateDTO> input;

    public CompoundActionCreateDTO(final CompoundActionCreateEvent event) {
        this.name = event.getName();
        List<String> keys = new ArrayList<>(event.getInput().keySet());
        List<String> values = new ArrayList<>(event.getInput().values());
        List<ActionParamsCreateDTO> list = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++)   {
            list.add(new ActionParamsCreateDTO(keys.get(i), values.get(i)));
        }
        this.input = list;
    }
}
