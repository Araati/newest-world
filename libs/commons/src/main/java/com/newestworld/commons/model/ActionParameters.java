package com.newestworld.commons.model;

import com.newestworld.commons.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

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

    @AllArgsConstructor
    @NoArgsConstructor
    class Impl implements ActionParameters {

        private List<ActionParameter> parameters;

        public List<ActionParameter> getAll() {
            return parameters;
        }
    }
}
