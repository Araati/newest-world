package com.newestworld.commons.model;

import java.time.LocalDateTime;
import java.util.Map;

public interface AbstractObject {

    long getId();

    long getStructureId();

    String getName();

    Map<String, String> getProperties();

    LocalDateTime getCreatedAt();

}
