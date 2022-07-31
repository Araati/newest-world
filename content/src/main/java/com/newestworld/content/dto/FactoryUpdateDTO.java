package com.newestworld.content.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
public class FactoryUpdateDTO {

    @JsonProperty(value = "isWorking")
    private Boolean isWorking;

    @JsonProperty(value = "store")
    private Long store;

    public Optional<Boolean> isWorking()    {
        return Optional.ofNullable(isWorking);
    }

    public Optional<Long> getStore()    {
        return Optional.ofNullable(store);
    }

}
