package com.newestworld.content.dto;

import com.newestworld.commons.model.Node;
import com.newestworld.commons.model.ActionStructure;
import com.newestworld.commons.model.ModelParameters;
import com.newestworld.content.model.entity.ActionStructureEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionStructureDTO implements ActionStructure {
    
    private long id;
    
    private String name;

    private ModelParameters parameters;
    
    private List<Node> steps;
    
    private LocalDateTime createdAt;
    
    public ActionStructureDTO(final ActionStructureEntity source, final ModelParameters parameters, final List<Node> steps) {
        this.id = source.getId();
        this.name = source.getName();
        this.parameters = parameters;
        this.steps = steps;
        this.createdAt = source.getCreatedAt();
    }
}
