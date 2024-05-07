package com.newestworld.content.model.entity;

import com.newestworld.content.dto.NodeCreateDTO;
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
@Table(name = "node")
public class NodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "model")
    private long id;

    private Long position;

    private long structureId;

    private int type;

    private boolean deleted;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public NodeEntity(final long structureId, final NodeCreateDTO source) {
        this.structureId = structureId;
        this.position = source.getPosition();
        this.type = source.getType();
    }
}
