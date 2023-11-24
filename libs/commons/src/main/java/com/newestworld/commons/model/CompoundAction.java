package com.newestworld.commons.model;

import java.time.LocalDateTime;

public interface CompoundAction {

    long getId();

    ActionParameters getInput();

    LocalDateTime getCreatedAt();

}
