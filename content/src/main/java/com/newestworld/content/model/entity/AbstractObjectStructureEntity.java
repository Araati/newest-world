package com.newestworld.content.model.entity;

import com.newestworld.content.dto.AbstractObjectStructureCreateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@With
@Entity
@Table(name = "abstract_object_structure")
public class AbstractObjectStructureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "structure")
    private long id;

    @Column(unique = true)
    private String name;

    private boolean deleted;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public AbstractObjectStructureEntity(final AbstractObjectStructureCreateDTO request) {
        this.name = request.getName();
    }
}
