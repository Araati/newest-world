package com.newestworld.content.dto;

import com.newestworld.commons.model.ModelParameter;
import com.newestworld.commons.model.Node;
import com.newestworld.commons.model.ActionStructure;
import com.newestworld.content.model.entity.ActionStructureEntity;
import com.newestworld.content.model.entity.ModelParameterEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionStructureDTO implements ActionStructure {
    
    private long id;
    
    private String name;

    private List<ModelParameter> parameters = new ArrayList<>();
    
    private List<Node> steps;
    
    private LocalDateTime createdAt;
    
    public ActionStructureDTO(final ActionStructureEntity source, final List<Node> steps) {
        this.id = source.getId();
        this.name = source.getName();
        for (ModelParameterEntity entity : source.getParameters()) {
            parameters.add(new ModelParameter(
                    entity.getName(),
                    entity.isRequired(),
                    entity.getData(),
                    entity.getType(),
                    entity.getMax(),
                    entity.getMin()
            ));
        }
        this.steps = steps;
        this.createdAt = source.getCreatedAt();
    }
}
