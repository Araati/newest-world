package com.newestworld.commons.model;

import java.time.LocalDateTime;

public interface Node {

    long getId();

    Long getLocalPosition();

    ActionType getType();

    ModelParameters getParameters();

    LocalDateTime getCreatedAt();

}
