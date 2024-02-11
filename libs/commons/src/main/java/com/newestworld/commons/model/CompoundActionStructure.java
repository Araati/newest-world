package com.newestworld.commons.model;

import java.time.LocalDateTime;
import java.util.List;

public interface CompoundActionStructure {

    long getId();

    String getName();

    List<String> getInput();

    // TODO: 25.11.2023 Хранить степы здесь как BasicActions не лучшая идея, возможно. Может быть лучше вложенный класс?
    List<BasicAction> getSteps();

    LocalDateTime getCreatedAt();

}
