package com.newestworld.content.model.entity;

import com.newestworld.content.dto.ActionStructureCreateDTO;
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
@Table(name = "action_structure")
public class ActionStructureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "model")
    private long id;

    @Column(unique = true)
    private String name;

    private boolean deleted;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public ActionStructureEntity(final ActionStructureCreateDTO request) {
        this.name = request.getName();
    }
}
