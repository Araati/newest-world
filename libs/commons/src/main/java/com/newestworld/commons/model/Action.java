package com.newestworld.commons.model;

import com.newestworld.commons.dto.ActionParams;

import java.time.LocalDateTime;
import java.util.List;

public interface Action {

    long getId();

    ActionType getType();

    LocalDateTime getCreatedAt();

}
