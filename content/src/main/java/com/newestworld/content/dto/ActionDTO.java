package com.newestworld.content.dto;

import com.newestworld.commons.model.Action;
import com.newestworld.commons.model.ActionParameters;
import com.newestworld.commons.model.ActionType;
import com.newestworld.content.model.entity.ActionEntity;
import com.newestworld.content.model.entity.ActionTimeoutEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionDTO implements Action {

    private long id;

    private ActionType type;

    private long timeout;

    private ActionParameters parameters;

    private boolean inProgress;

    private LocalDateTime createdAt;

    public ActionDTO(final ActionEntity source, final ActionParameters source1, final ActionTimeoutEntity source2)    {
        this.id = source.getId();
        this.type = ActionType.decode(source.getType());
        this.timeout = source2.getTimeout();
        this.parameters = source1;
        this.inProgress = source.isInProgress();
        this.createdAt = source.getCreatedAt();
    }

}
