package com.newestworld.commons.model;

import java.time.LocalDateTime;
import java.util.Map;

public interface Action {

    long getId();

    long getStructureId();

    String getName();

    Map<String, String> getParameters();

    LocalDateTime getCreatedAt();

}
