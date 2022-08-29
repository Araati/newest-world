package com.newestworld.executor.dto;

import com.newestworld.commons.model.Action;
import com.newestworld.commons.dto.ActionParams;
import com.newestworld.commons.model.ActionType;
import com.newestworld.executor.model.entity.ActionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionDTO implements Action {

    private long id;

    private ActionType type;

    private LocalDateTime createdAt;

    public ActionDTO(final ActionEntity source) {
        this.id = source.getId();
        this.type = source.getType();
        this.createdAt = source.getCreatedAt();
    }
}