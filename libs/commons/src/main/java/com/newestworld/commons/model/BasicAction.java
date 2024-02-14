package com.newestworld.commons.model;

import java.time.LocalDateTime;

public interface BasicAction {

    long getId();

    Long getLocalPosition();

    ActionType getType();

    ActionParameters getParameters();

    LocalDateTime getCreatedAt();

}
