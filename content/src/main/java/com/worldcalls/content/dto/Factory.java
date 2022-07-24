package com.worldcalls.content.dto;

import java.time.LocalDateTime;

public interface Factory {

    long getId();

    boolean isWorking();

    long getStore();

    LocalDateTime getCreatedAt();

}
