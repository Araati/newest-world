package com.newestworld.commons.model;

import java.time.LocalDateTime;
import java.util.Map;

public interface Node {

    long getId();

    Long getPosition();

    ActionType getType();

    Map<String, String> getParameters();

    LocalDateTime getCreatedAt();

}
