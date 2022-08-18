package com.newestworld.scheduler.dto;

import com.newestworld.commons.model.ActionParameters;
import com.newestworld.commons.model.ActionType;
import com.newestworld.commons.model.IdReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionDTO implements IdReference {

    private long id;
}
