package com.newestworld.content.dto;

import com.newestworld.streams.event.AbstractObjectUpdateEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AbstractObjectUpdateDTO {

    private long id;

    private Map<String, String> parameters;

    public AbstractObjectUpdateDTO(final AbstractObjectUpdateEvent event) {
        this.id = event.getId();
        this.parameters = event.getProperties();
    }
}
