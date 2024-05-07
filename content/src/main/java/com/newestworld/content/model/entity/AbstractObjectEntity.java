package com.newestworld.content.model.entity;

import com.newestworld.commons.model.AbstractObjectStructure;
import com.newestworld.content.dto.AbstractObjectCreateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@With
@Entity
@Table(name = "abstract_object")
public class AbstractObjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long structureId;

    private String name;

    @ElementCollection
    private Map<String, String> parameters;

    private boolean deleted;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public AbstractObjectEntity(final AbstractObjectCreateDTO request, final AbstractObjectStructure structure) {
        this.structureId = structure.getId();
        this.name = request.getName();
    }
}
