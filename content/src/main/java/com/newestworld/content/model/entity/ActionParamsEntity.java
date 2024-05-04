package com.newestworld.content.model.entity;

import com.newestworld.content.dto.ActionParamsCreateDTO;
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
@Table(name = "action_params")
public class ActionParamsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "action_id")
    private long actionId;

    private String name;

    private String data;

    private boolean deleted;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public ActionParamsEntity(final long id, final ActionParamsCreateDTO source) {
        this.actionId = id;
        this.name = source.getName();
        this.data = source.getValue();
    }
}
