package com.newestworld.commons.model;

import java.time.LocalDateTime;

public interface CompoundAction {

    long getId();

    String getName();

    long getStructureId();

    ActionParameters getInput();

    LocalDateTime getCreatedAt();

}
