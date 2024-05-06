package com.newestworld.content.dto;

import com.newestworld.commons.model.ModelParameters;
import com.newestworld.commons.model.ActionType;
import com.newestworld.commons.model.Node;
import com.newestworld.content.model.entity.NodeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NodeDTO implements Node {

    private long id;

    private Long position;

    private ActionType type;

    private ModelParameters parameters;

    private LocalDateTime createdAt;

    public NodeDTO(final NodeEntity source, final ModelParameters modelParameters)   {
        this.id = source.getId();
        this.position = source.getPosition();
        this.type = ActionType.decode(source.getType());
        this.parameters = modelParameters;
        this.createdAt = source.getCreatedAt();
    }
}
