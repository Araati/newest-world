package com.newestworld.content.dto;

import com.newestworld.commons.model.AbstractObjectStructure;
import com.newestworld.commons.model.ModelParameter;
import com.newestworld.content.model.entity.AbstractObjectStructureEntity;
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
public class AbstractObjectStructureDTO implements AbstractObjectStructure {

    private long id;

    private String name;

    private List<ModelParameter> parameters = new ArrayList<>();

    private LocalDateTime createdAt;

    public AbstractObjectStructureDTO(final AbstractObjectStructureEntity source) {
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
        this.createdAt = source.getCreatedAt();
    }
}
