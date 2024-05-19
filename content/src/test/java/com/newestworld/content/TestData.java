package com.newestworld.content;

import com.newestworld.commons.model.ActionType;
import com.newestworld.commons.model.ModelParameter;
import com.newestworld.content.dto.*;
import com.newestworld.streams.event.AbstractObjectCreateEvent;
import com.newestworld.streams.event.AbstractObjectUpdateEvent;
import com.newestworld.streams.event.ActionCreateEvent;
import com.newestworld.streams.event.NodeEvent;

import java.util.List;
import java.util.Map;

public class TestData {

    // Object Structure
    public static String objectStructureName = "factory";
    public static List<ModelParameter> objectStructureParameters = List.of(
            new ModelParameter("store", true, "0", "int", 100_000L, 0L),
            new ModelParameter("type", false, "steel", "string", null, null)
    );
    public static AbstractObjectStructureCreateDTO objectStructureCreateDTO =
            new AbstractObjectStructureCreateDTO(objectStructureName, objectStructureParameters);
    public static int expectedObjectStructureId = 1;



    // Object
    public static Map<String, String> objectParameters = Map.of("store", "1000");
    public static Map<String, String> expectedObjectParameters = Map.of(
            "type", "steel",
            "store", "1000"
    );
    public static int expectedObjectId = 1;
    public static AbstractObjectCreateEvent objectCreateEvent = new AbstractObjectCreateEvent(objectStructureName, objectParameters);
    public static AbstractObjectCreateDTO objectCreateDTO  = new AbstractObjectCreateDTO(objectStructureName, objectParameters);

    public static AbstractObjectUpdateEvent validObjectUpdateEvent =
            new AbstractObjectUpdateEvent(expectedObjectId, Map.of("store", "10000"));
    public static Map<String, String> expectedObjectUpdateParameters = Map.of(
            "type", "steel",
            "store", "10000"
    );

    public static AbstractObjectUpdateEvent invalidObjectUpdateEvent =
            new AbstractObjectUpdateEvent(expectedObjectId, Map.of("store", "1000000"));



    // Action Structure
    public static String actionStructureName = "produce";
    public static List<ModelParameter> actionStructureParameters = List.of(
            new ModelParameter("target_id", true, null, "int", null, null),
            new ModelParameter("amount", false, "10000", "int", null, 10_000L)
    );
    public static List<NodeCreateDTO> nodeCreateDTOList = List.of(
            new NodeCreateDTO(ActionType.START.getId(), 1L, Map.of("next", "3")),
            new NodeCreateDTO(ActionType.END.getId(), 2L, Map.of()),
            new NodeCreateDTO(ActionType.CREATE_ACTION.getId(), 3L, Map.of(
                    "objectStructureName", "factory",
                    "next", "4"
            )),
            new NodeCreateDTO(ActionType.MODIFY.getId(), 4L, Map.of(
                    "target", "$target_id",
                    "field", "store",
                    "value", "$amount",
                    "next", "2"
            ))
    );
    public static ActionStructureCreateDTO actionStructureCreateDTO = new ActionStructureCreateDTO(
            actionStructureName,
            actionStructureParameters,
            nodeCreateDTOList);
    public static int expectedActionStructureId = 1;

     public static List<NodeEvent> expectedNodeEvents = nodeCreateDTOList.stream()
             .map(x -> new NodeEvent(x.getPosition(), x.getType(), x.getParameters())).toList();



    // Action
    public static Map<String, String> actionParameters = Map.of("target_id", "1");
    public static ActionCreateDTO actionCreateDTO = new ActionCreateDTO(
            actionStructureName,
            actionParameters
    );
    public static Map<String, String> expectedActionParameters = Map.of(
            "amount", "10000",
            "target_id", "1"
    );
    public static int expectedActionId = 52;

    public static ActionCreateEvent actionCreateEvent = new ActionCreateEvent(actionStructureName, actionParameters);

}
