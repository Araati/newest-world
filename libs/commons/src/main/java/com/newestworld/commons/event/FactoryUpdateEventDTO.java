package com.newestworld.commons.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FactoryUpdateEventDTO {

    private long factoryId;
    private Boolean working;
    private Long amount;

    public Optional<Boolean> getWorking() {
        return Optional.ofNullable(working);
    }

    public Optional<Long> getAmount() {
        return Optional.ofNullable(amount);
    }
}
