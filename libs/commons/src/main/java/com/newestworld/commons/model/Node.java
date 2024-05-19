package com.newestworld.commons.model;

import java.time.LocalDateTime;
import java.util.Map;

public interface Node {

    Long getPosition();

    ActionType getType();

    Map<String, String> getParameters();

}
