package com.newestworld.commons.model;

import java.time.LocalDateTime;
import java.util.Map;

public interface AbstractObjectStructure {

    long getId();

    String getName();

    Map<String, String> getProperties();

    LocalDateTime getCreatedAt();

}
