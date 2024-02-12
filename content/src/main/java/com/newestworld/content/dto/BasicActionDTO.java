package com.newestworld.content.dto;

import com.newestworld.commons.model.ActionParameters;
import com.newestworld.commons.model.ActionType;
import com.newestworld.commons.model.BasicAction;
import com.newestworld.content.model.entity.BasicActionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasicActionDTO implements BasicAction {

    private long id;

    private Long localPosition;

    private ActionType type;

    private ActionParameters parameters;

    private LocalDateTime createdAt;

    public BasicActionDTO(final BasicActionEntity source, final ActionParameters actionParameters)   {
        this.id = source.getId();
        this.localPosition = source.getLocalPosition();
        this.type = ActionType.decode(source.getType());
        this.parameters = actionParameters;
        this.createdAt = source.getCreatedAt();
    }
}
