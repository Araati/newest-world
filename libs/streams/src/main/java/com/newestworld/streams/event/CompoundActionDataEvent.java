package com.newestworld.streams.event;

import com.newestworld.commons.model.ActionParameters;
import com.newestworld.commons.model.BasicAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompoundActionDataEvent {

    private ActionParameters input;

    private List<BasicActionEvent> basicActions;

}
