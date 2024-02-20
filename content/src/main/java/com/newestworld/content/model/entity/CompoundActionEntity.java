package com.newestworld.content.model.entity;

import com.newestworld.content.dto.CompoundActionCreateDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@With
@Entity
@Table(name = "compound_action")
public class CompoundActionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;

    private long structureId;

    private boolean deleted;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public CompoundActionEntity(final CompoundActionCreateDTO source, final long structureId) {
        this.name = source.getName();
        this.structureId = structureId;
    }
}
