package com.newestworld.commons.dto;

import java.time.LocalDateTime;

public interface Action {

    long getId();

    int getType();

    LocalDateTime getCreatedAt();

}
