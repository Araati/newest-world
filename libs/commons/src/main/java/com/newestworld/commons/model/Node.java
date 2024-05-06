package com.newestworld.commons.model;

import java.time.LocalDateTime;

public interface Node {

    long getId();

    Long getPosition();

    ActionType getType();

    ModelParameters getParameters();

    LocalDateTime getCreatedAt();

}
