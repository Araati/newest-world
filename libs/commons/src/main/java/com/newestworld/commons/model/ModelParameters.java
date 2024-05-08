package com.newestworld.commons.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.newestworld.commons.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@JsonDeserialize(as = ModelParameters.Impl.class)
public interface ModelParameters {

    List<ModelParameter> getAll();

    void add(final ModelParameter parameter);

    default Optional<ModelParameter> getByName(final String name) {
        return getAll().stream().filter(x -> x.getName().equals(name)).findFirst();
    }

    default ModelParameter mustGetByName(final String name) {
        return getByName(name).orElseThrow(() -> new ResourceNotFoundException("Model parameter", name));
    }

    default boolean contains(final String name) {
        return getByName(name).isPresent();
    }

    default int getSize() {
        return getAll().size();
    }

    default boolean isEmpty() {
        return getSize() == 0;
    }

    @NoArgsConstructor
    class Impl implements ModelParameters   {

        @Valid
        @JsonValue
        private List<ModelParameter> parameters;

        public List<ModelParameter> getAll() {
            return parameters;
        }

        public void add(final ModelParameter parameter)   {
            parameters.add(parameter);
        }

        @JsonCreator
        public Impl(final List<ModelParameter> parameters) {
            this.parameters = parameters;
        }

    }
}
