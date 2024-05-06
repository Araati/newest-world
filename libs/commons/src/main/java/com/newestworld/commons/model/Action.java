package com.newestworld.commons.model;

import java.time.LocalDateTime;

public interface Action {

    long getId();

    String getName();

    long getStructureId();

    ModelParameters getParameters();

    LocalDateTime getCreatedAt();

}
