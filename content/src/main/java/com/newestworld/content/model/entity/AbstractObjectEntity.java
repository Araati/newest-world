package com.newestworld.content.model.entity;

import com.newestworld.commons.model.AbstractObjectStructure;
import com.newestworld.content.dto.AbstractObjectCreateDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
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

    // Инициализация для того, чтобы не было пересечений с мапой в structure
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, String> properties = new HashMap<>();

    private boolean deleted;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public AbstractObjectEntity(final AbstractObjectCreateDTO request, final AbstractObjectStructure structure) {
        this.structureId = structure.getId();
        this.name = request.getName();
        this.properties.putAll(structure.getProperties());
        this.properties.putAll(request.getProperties());
    }
}
