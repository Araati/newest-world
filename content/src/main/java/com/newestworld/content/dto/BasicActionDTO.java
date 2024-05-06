package com.newestworld.content.dto;

import com.newestworld.commons.model.ModelParameters;
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

    private ModelParameters parameters;

    private LocalDateTime createdAt;

    public BasicActionDTO(final BasicActionEntity source, final ModelParameters modelParameters)   {
        this.id = source.getId();
        this.localPosition = source.getLocalPosition();
        this.type = ActionType.decode(source.getType());
        this.parameters = modelParameters;
        this.createdAt = source.getCreatedAt();
    }
}
