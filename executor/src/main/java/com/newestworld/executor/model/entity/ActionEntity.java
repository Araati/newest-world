package com.newestworld.executor.model.entity;

import com.newestworld.commons.model.ActionType;
import com.newestworld.commons.util.converter.ActionTypeConverter;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "type")
    @Convert(converter = ActionTypeConverter.class)
    private ActionType type;

    @Column(name = "in_progress")
    private boolean inProgress;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

}
