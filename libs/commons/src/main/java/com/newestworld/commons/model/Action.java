package com.newestworld.commons.model;

import java.time.LocalDateTime;
import java.util.Map;

public interface Action {

    long getId();

    String getName();

    long getStructureId();

    Map<String, String> getParameters();

    LocalDateTime getCreatedAt();

}
