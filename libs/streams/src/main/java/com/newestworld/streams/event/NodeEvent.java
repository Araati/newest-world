package com.newestworld.streams.event;

import com.newestworld.commons.model.Node;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NodeEvent implements Event {

    private Long position;

    // Енамка не передается нормально, разбираться мне лень
    private int type;

    private Map<String, String> parameters;

    public NodeEvent(final Node node) {
        this.position = node.getPosition();
        this.type = node.getType().getId();
        this.parameters = node.getParameters();
    }
}
