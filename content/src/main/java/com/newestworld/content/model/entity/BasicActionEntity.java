package com.newestworld.content.model.entity;

import com.newestworld.content.dto.BasicActionCreateDTO;
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
@Table(name = "basic_action")
public class BasicActionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private Long localPosition;

    private long structureId;

    private int type;

    // TODO: 25.11.2023 boolean inProgress

    private boolean deleted;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public BasicActionEntity(final long structureId, final BasicActionCreateDTO source) {
        this.structureId = structureId;
        this.localPosition = source.getLocalPosition();
        this.type = source.getType();
    }
}
