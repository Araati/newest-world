package com.newestworld.scheduler.model.entity;

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
@Table(name = "action_timeout")
public class ActionTimeoutEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "action_id")
    private long actionId;

    @Column(name = "timeout")
    private long timeout;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public ActionTimeoutEntity(final long actionId, final long timeout)    {
        this.actionId = actionId;
        this.timeout = timeout;
    }

}
