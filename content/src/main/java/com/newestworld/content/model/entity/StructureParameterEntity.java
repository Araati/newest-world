package com.newestworld.content.model.entity;

import com.newestworld.content.dto.StructureParameterCreateDTO;
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
@Table(name = "structure_parameter")
public class StructureParameterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long structureId;

    private String name;

    private boolean required;

    private String type;

    private String init;

    private Long max;
    private Long min;

    private boolean deleted;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public StructureParameterEntity(final long id, final StructureParameterCreateDTO source) {
        this.structureId = id;
        this.name = source.getName();
        this.required = source.isRequired();
        this.type = source.getType();
        this.init = source.getInit();
        this.max = source.getMax();
        this.min = source.getMin();
    }
}
