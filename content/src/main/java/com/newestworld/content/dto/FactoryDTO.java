package com.newestworld.content.dto;

import com.newestworld.content.model.entity.FactoryEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FactoryDTO implements Factory {

    private long id;

    // FIXME: 01.08.2022 isIsWorking
    private boolean isWorking;

    private long store;

    private LocalDateTime createdAt;

    public FactoryDTO(final FactoryEntity source)   {
        this.id = source.getId();
        this.isWorking = source.isWorking();
        this.store = source.getStore();
        this.createdAt = source.getCreatedAt();
    }

    // TODO: 27.04.2022 Создание с ентити

}
