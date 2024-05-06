package com.newestworld.content.model.entity;

import com.newestworld.content.dto.ModelParameterCreateDTO;
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

    private long modelId;

    private String name;

    private boolean required;

    private String value;

    private String type;

    private String init;

    private Long min;
    private Long max;

    private boolean deleted;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public ModelParameterEntity(final long id, final ModelParameterCreateDTO source) {
        this.modelId = id;
        this.name = source.getName();
        this.value = source.getValue();
    }
}
