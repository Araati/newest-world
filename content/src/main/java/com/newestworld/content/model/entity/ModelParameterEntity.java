package com.newestworld.content.model.entity;

import com.newestworld.commons.model.ModelParameter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@With
@Entity
@Table(name = "model_parameter")
public class ModelParameterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private boolean required;

    private String data;

    private String type;


    private Long max;
    private Long min;

    private boolean deleted;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public ModelParameterEntity(final ModelParameter source) {
        this.name = source.getName();
        this.required = source.isRequired();
        this.type = source.getType();
        this.data = source.getData();
        this.max = source.getMax();
        this.min = source.getMin();
    }
}
