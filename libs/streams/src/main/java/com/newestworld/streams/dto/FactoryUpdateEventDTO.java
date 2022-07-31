package com.newestworld.streams.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FactoryUpdateEventDTO {

    private long factoryId;
    private long amount;

}
