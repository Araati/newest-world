package com.newestworld.content.model.entity;

import com.newestworld.content.dto.ActionCreateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@With
@Entity
@Table(name = "action")
public class ActionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "model")
    private long id;

    private long structureId;

    private String name;


    private boolean deleted;

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, String> parameters;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public ActionEntity(final ActionCreateDTO source, final Map<String, String> parameters, final long structureId) {
        this.name = source.getName();
        this.structureId = structureId;
        this.parameters = parameters;
    }
}
