package com.newestworld.commons.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface AbstractObjectStructure {

    long getId();

    String getName();

    ModelParameters getParameters();

    LocalDateTime getCreatedAt();

}
