package com.newestworld.commons.dto;

import java.time.LocalDateTime;
import java.util.List;

public interface Action {

    long getId();

    int getType();

    List<ActionParams> getParams();

    LocalDateTime getCreatedAt();

}
