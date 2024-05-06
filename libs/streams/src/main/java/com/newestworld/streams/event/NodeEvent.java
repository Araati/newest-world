package com.newestworld.streams.event;

import com.newestworld.commons.model.ModelParameters;
import com.newestworld.commons.model.Node;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NodeEvent implements Event {

    private long id;

    private Long position;

    // Енамка не передается нормально, разбираться мне лень
    private int type;

    private ModelParameters parameters;

    private LocalDateTime createdAt;

    public NodeEvent(final Node node) {
        this.id = node.getId();
        this.position = node.getPosition();
        this.type = node.getType().getId();
        this.parameters = node.getParameters();
        this.createdAt = node.getCreatedAt();
    }
}
