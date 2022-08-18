package com.newestworld.commons.event;

import com.newestworld.commons.model.ActionParameters;
import com.newestworld.commons.model.ActionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionTimeoutEvent {

    private long id;

}
