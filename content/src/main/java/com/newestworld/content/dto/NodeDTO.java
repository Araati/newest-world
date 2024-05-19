package com.newestworld.content.dto;

import com.newestworld.commons.model.ActionType;
import com.newestworld.commons.model.Node;
import com.newestworld.content.model.entity.NodeEntity;
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

    public NodeDTO(final NodeEntity source)   {
        this.position = source.getPosition();
        this.type = ActionType.decode(source.getType());
        this.parameters = source.getParameters();
    }
}
