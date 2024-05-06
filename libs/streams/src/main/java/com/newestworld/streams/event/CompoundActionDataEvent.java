package com.newestworld.streams.event;

import com.newestworld.commons.model.ModelParameters;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompoundActionDataEvent implements Event {

    private Long actionId;

    private ModelParameters input;

    private List<NodeEvent> nodes;

}
