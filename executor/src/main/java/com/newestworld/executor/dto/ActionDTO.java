package com.newestworld.executor.dto;

import com.newestworld.commons.dto.Action;
import com.newestworld.commons.dto.ActionParams;
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

    private int type;

    private List<ActionParams> params;

    private LocalDateTime createdAt;

    public ActionDTO(final ActionEntity source, final List<ActionParams> source1) {
        this.id = source.getId();
        this.type = source.getType();
        this.params = source1;
        this.createdAt = source.getCreatedAt();
    }
}