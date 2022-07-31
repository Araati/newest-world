package com.newestworld.streams.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FactoryUpdateEventDTO {

    private long factoryId;
    private Optional<Boolean> working;
    private Optional<Long> amount;

}
