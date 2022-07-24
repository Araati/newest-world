package com.worldcalls.content.model.entity;

import com.worldcalls.content.dto.ActionCreateDTO;
import com.worldcalls.content.dto.ActionParamsCreateDTO;
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
@Table(name = "action_params")
public class ActionParamsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "action_id")
    private long actionId;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public ActionParamsEntity(final long id, final ActionParamsCreateDTO source) {
        this.actionId = id;
        this.name = source.getName();
        this.value = source.getValue();
    }

}
