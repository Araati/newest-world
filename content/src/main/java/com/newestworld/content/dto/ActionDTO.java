package com.newestworld.content.dto;

import com.newestworld.content.model.entity.ActionEntity;
import com.newestworld.content.model.entity.ActionTimeoutEntity;
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

    private long timeout;

    private List<ActionParams> params;

    private boolean inProgress;

    private LocalDateTime createdAt;

    public ActionDTO(final ActionEntity source, final List<ActionParams> source1, final ActionTimeoutEntity source2)    {
        this.id = source.getId();
        this.type = source.getType();
        this.timeout = source2.getTimeout();
        this.params = source1;
        this.inProgress = source.isInProgress();
        this.createdAt = source.getCreatedAt();
    }

}
