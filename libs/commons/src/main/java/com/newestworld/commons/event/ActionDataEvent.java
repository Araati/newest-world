package com.newestworld.commons.event;

import com.newestworld.commons.model.Action;
import com.newestworld.commons.model.ActionParameters;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionDataEvent {

    private Action action;
    private ActionParameters actionParameters;

}
