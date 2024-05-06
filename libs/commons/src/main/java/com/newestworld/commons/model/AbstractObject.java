package com.newestworld.commons.model;

import java.time.LocalDateTime;

public interface AbstractObject {

    long getId();

    long getStructureId();

    String getName();

    ModelParameters getParameters();

    LocalDateTime getCreatedAt();

}
