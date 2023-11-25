package com.newestworld.streams.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.newestworld.commons.model.ActionParameter;
import com.newestworld.commons.model.ActionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ActionDataEvent {

    private long id;
    private String name;

    //Параметры передаются в списке, а не в ActionParameters, потому что иначе боль с десериализацией
    private List<ActionParameter> parameters;
    private LocalDateTime createdAt;

    @JsonCreator
    public ActionDataEvent(long id,
                           @JsonProperty(value = "name") final String name,
                           @JsonProperty(value = "parameters") final List<ActionParameter> parameters,
                           final LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.parameters = parameters;
        this.createdAt = createdAt;
    }
}
