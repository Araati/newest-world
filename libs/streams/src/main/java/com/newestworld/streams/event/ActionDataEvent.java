package com.newestworld.streams.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.newestworld.commons.model.Action;
import com.newestworld.commons.model.ActionParameter;
import com.newestworld.commons.model.ActionParameters;
import com.newestworld.commons.model.ActionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionDataEvent {

    private long id;
    private ActionType type;

    //Параметры передаются в списке, а не в ActionParameters, потому что иначе боль с десериализацией
    private List<ActionParameter> parameters;
    private LocalDateTime createdAt;

    @JsonCreator
    public ActionDataEvent(long id,
                           @JsonProperty(value = "type") final int type,
                           @JsonProperty(value = "parameters") final List<ActionParameter> parameters,
                           final LocalDateTime createdAt) {
        this.id = id;
        this.type = ActionType.decode(type);
        this.parameters = parameters;
        this.createdAt = createdAt;
    }
}
