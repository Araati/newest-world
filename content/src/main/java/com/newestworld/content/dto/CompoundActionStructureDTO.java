package com.newestworld.content.dto;

import com.newestworld.commons.model.BasicAction;
import com.newestworld.commons.model.CompoundActionStructure;
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

    private List<String> input;
    
    private List<BasicAction> steps;
    
    private LocalDateTime createdAt;
    
    public CompoundActionStructureDTO(final CompoundActionStructureEntity source, final List<BasicAction> steps) {
        this.id = source.getId();
        this.name = source.getName();
        this.input = source.getInput();
        this.steps = steps;
        this.createdAt = source.getCreatedAt();
    }
}
