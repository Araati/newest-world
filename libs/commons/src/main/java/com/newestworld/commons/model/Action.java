package com.newestworld.commons.model;

import java.time.LocalDateTime;

public interface Action {

    long getId();

    ActionType getType();

    // TODO: 31.01.2023 Стоит ли засовывать сюда параметры? Отличия в имплементации экшенов меня уже сильно настораживают, если честно
    ActionParameters getParameters();

    LocalDateTime getCreatedAt();

}
