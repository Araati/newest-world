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

    //fixme id of node is not required ANYWHERE
    private long id;

    private Long position;

    private ActionType type;

    private Map<String, String> parameters;

    private LocalDateTime createdAt;

    public NodeDTO(final NodeEntity source)   {
        this.id = source.getId();
        this.position = source.getPosition();
        this.type = ActionType.decode(source.getType());
        this.parameters = source.getParameters();
        this.createdAt = source.getCreatedAt();
    }
}
