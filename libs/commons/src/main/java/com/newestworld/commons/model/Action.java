package com.newestworld.commons.model;

import java.time.LocalDateTime;

public interface Action {

    long getId();

    ActionType getType();

    LocalDateTime getCreatedAt();

}
