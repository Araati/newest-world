package com.newestworld.executor.dto;

import com.newestworld.commons.model.ActionType;
import com.newestworld.commons.model.Node;
import com.newestworld.streams.event.NodeEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NodeDTO implements Node {

    private Long position;

    private ActionType type;

    private Map<String, String> parameters;

    public NodeDTO(final NodeEvent nodeEvent) {
        this.position = nodeEvent.getPosition();
        this.type = ActionType.decode(nodeEvent.getType());
        this.parameters = nodeEvent.getParameters();
        this.createdAt = nodeEvent.getCreatedAt();
    }
}