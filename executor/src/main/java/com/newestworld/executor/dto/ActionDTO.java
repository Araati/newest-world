package com.newestworld.executor.dto;

import com.newestworld.commons.model.Action;
import com.newestworld.commons.model.ActionParameters;
import com.newestworld.commons.model.ActionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionDTO implements Action {

    private long id;

    private ActionType type;

    private ActionParameters parameters;

    private LocalDateTime createdAt;

}