package com.newestworld.commons.model;

import java.time.LocalDateTime;
import java.util.List;

public interface AbstractObjectStructure {

    long getId();

    String getName();

    List<ModelParameter> getParameters();

    LocalDateTime getCreatedAt();

}
