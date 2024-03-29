package com.newestworld.content.model.entity;

import com.newestworld.content.dto.AbstractObjectStructureCreateDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@With
@Entity
@Table(name = "abstract_object_structure")
public class AbstractObjectStructureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, String> properties;

    private boolean deleted;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public AbstractObjectStructureEntity(final AbstractObjectStructureCreateDTO request) {
        this.name = request.getName();
        this.properties = request.getProperties();
    }
}
