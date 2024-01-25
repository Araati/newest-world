package com.newestworld.commons.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.newestworld.commons.exception.ResourceNotFoundException;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@JsonDeserialize(as = ActionParameters.Impl.class)
public interface ActionParameters {

    List<ActionParameter> getAll();

    default Optional<ActionParameter> getByName(final String name) {
        return getAll().stream().filter(x -> x.getName().equals(name)).findFirst();
    }

    default ActionParameter mustGetByName(final String name) {
        return getByName(name).orElseThrow(() -> new ResourceNotFoundException("Action parameter", name));
    }

    default int getSize() {
        return getAll().size();
    }

    default boolean isEmpty() {
        return getSize() == 0;
    }

    @NoArgsConstructor
    class Impl implements ActionParameters {

        @JsonValue
        private List<ActionParameter> parameters;

        public List<ActionParameter> getAll() {
            return parameters;
        }

        @JsonCreator
        public Impl(final List<ActionParameter> parameters) {
            this.parameters = parameters;
        }
    }
}
