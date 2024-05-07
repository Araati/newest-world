package com.newestworld.streams.event;

import com.newestworld.commons.model.ModelParameters;
import com.newestworld.commons.model.StructureParameter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionDataEvent implements Event {

    private Long actionId;

    private List<StructureParameter> input;

    private List<NodeEvent> nodes;

}
