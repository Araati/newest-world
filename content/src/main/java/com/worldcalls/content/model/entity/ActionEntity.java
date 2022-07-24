package com.worldcalls.content.model.entity;

import com.worldcalls.content.dto.ActionCreateDTO;
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
@Table(name = "action")
public class ActionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "type")
    private int type;

    @Column(name = "in_progress")
    private boolean inProgress;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // TODO: 28.04.2022 Связь с парамс и таймаутом один к одному 

    public ActionEntity(final ActionCreateDTO source) {
        this.type = source.getType();
    }
}
