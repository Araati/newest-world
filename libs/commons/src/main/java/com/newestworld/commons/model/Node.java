package com.newestworld.commons.model;

import java.time.LocalDateTime;

public interface Node {

    long getId();

    Long getOrder();

    ActionType getType();

    ModelParameters getParameters();

    LocalDateTime getCreatedAt();

}
