package com.worldcalls.content.dto;

import java.time.LocalDateTime;

public interface Action {

    long getId();

    int getType();

    long getTimeout();

    boolean isInProgress();

    LocalDateTime getCreatedAt();

}
