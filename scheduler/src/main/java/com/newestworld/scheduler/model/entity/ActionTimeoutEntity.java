package com.newestworld.scheduler.model.entity;

import com.newestworld.scheduler.dto.ActionTimeoutCreateDTO;
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
@Table(name = "action_timeout")
public class ActionTimeoutEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "action_id")
    private long actionId;

    @Column(name = "timeout")
    private long timeout;

    @Column(name = "processing")
    private boolean processing;

    @Column(name = "deleted")
    private boolean deleted;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public ActionTimeoutEntity(final ActionTimeoutCreateDTO request) {
        this.actionId = request.getActionId();
        this.timeout = request.getTimeout() + System.currentTimeMillis();
    }
}
