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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "action_id")
    private long actionId;

    @Column(name = "type")
    private int type;

    // TODO: 25.11.2023 boolean inProgress

    @Column(name = "deleted")
    private boolean deleted;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public BasicActionEntity(final long actionId, final BasicActionCreateDTO source) {
        this.actionId = actionId;
        this.type = source.getType();
    }
}
