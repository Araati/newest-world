package com.newestworld.executor.dto;

import com.newestworld.commons.model.ModelParameters;
import com.newestworld.commons.model.ActionType;
import com.newestworld.commons.model.Node;
import com.newestworld.streams.event.NodeEvent;
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

    public NodeDTO(final NodeEvent nodeEvent) {
        this.id = nodeEvent.getId();
        this.position = nodeEvent.getPosition();
        this.type = ActionType.decode(nodeEvent.getType());
        this.parameters = nodeEvent.getParameters();
        this.createdAt = nodeEvent.getCreatedAt();
    }
}