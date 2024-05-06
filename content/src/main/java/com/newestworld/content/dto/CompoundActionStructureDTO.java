package com.newestworld.content.dto;

import com.newestworld.commons.model.Node;
import com.newestworld.commons.model.CompoundActionStructure;
import com.newestworld.commons.model.ModelParameters;
import com.newestworld.content.model.entity.CompoundActionStructureEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompoundActionStructureDTO implements CompoundActionStructure {
    
    private long id;
    
    private String name;

    private ModelParameters parameters;
    
    private List<Node> steps;
    
    private LocalDateTime createdAt;
    
    public CompoundActionStructureDTO(final CompoundActionStructureEntity source, final ModelParameters parameters, final List<Node> steps) {
        this.id = source.getId();
        this.name = source.getName();
        this.parameters = parameters;
        this.steps = steps;
        this.createdAt = source.getCreatedAt();
    }
}
