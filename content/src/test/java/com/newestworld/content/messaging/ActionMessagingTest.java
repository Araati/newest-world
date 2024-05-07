package com.newestworld.content.messaging;

import com.newestworld.commons.exception.ResourceNotFoundException;
import com.newestworld.commons.model.ActionType;
import com.newestworld.commons.model.Action;
import com.newestworld.content.ContentApplication;
import com.newestworld.content.dto.StructureParameterCreateDTO;
import com.newestworld.content.dto.NodeCreateDTO;
import com.newestworld.content.dto.ActionCreateDTO;
import com.newestworld.content.dto.ActionStructureCreateDTO;
import com.newestworld.content.service.ActionService;
import com.newestworld.content.service.ActionStructureService;
import com.newestworld.streams.event.*;
import com.newestworld.streams.event.batch.ActionDataRequestBatchEvent;
import com.newestworld.streams.event.batch.ActionDataBatchEvent;
import com.newestworld.streams.publisher.EventPublisher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Field;
import java.util.*;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = ContentApplication.class, properties = {"spring.profiles.active=test"})
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ActionMessagingTest {

    @Autowired
    private ActionStructureService actionStructureService;
    @Autowired
    private ActionService actionService;

    @Autowired
    private ActionCreateEventConsumer createEventConsumer;
    @Autowired
    private ActionDataRequestBatchEventConsumer dataRequestBatchEventConsumer;
    @Autowired
    private ActionDeleteEventConsumer actionDeleteEventConsumer;

    @Autowired
    private EventPublisher<ActionTimeoutCreateEvent> actionTimeoutCreateEventPublisher;
    @Autowired
    private EventPublisher<ActionDataBatchEvent> actionDataBatchEventPublisher;

    @BeforeEach
    void setUp() throws IllegalAccessException {
        actionTimeoutCreateEventPublisher = mock(EventPublisher.class);
        Field field = ReflectionUtils
                .findFields(ActionService.class, f -> f.getName().equals("actionTimeoutCreateEventPublisher"),
                        ReflectionUtils.HierarchyTraversalMode.TOP_DOWN)
                .get(0);

        field.setAccessible(true);
        field.set(actionService, actionTimeoutCreateEventPublisher);
    }
    @Test
    void actionCreateEventConsume() {

        createTestAction();

        Map<String, String> properties = new HashMap<>();
        properties.put("$targetId", "1");
        properties.put("$amount", "1000");
        createEventConsumer.accept(new ActionCreateEvent("test", properties));

        Action action = actionService.findById(3);
        Assertions.assertSame("test", action.getName());
        Assertions.assertEquals(1, action.getStructureId());
        Assertions.assertEquals("1", action.getInput().mustGetByName("$targetId").getValue());
        Assertions.assertEquals("1000", action.getInput().mustGetByName("$amount").getValue().toString());
    }

    @Test
    void actionDataRequestBatchEventConsume() throws IllegalAccessException {
        actionDataBatchEventPublisher = mock(EventPublisher.class);
        Field field = ReflectionUtils
                .findFields(ActionDataRequestBatchEventConsumer.class, f -> f.getName().equals("actionDataBatchEventPublisher"),
                        ReflectionUtils.HierarchyTraversalMode.TOP_DOWN)
                .get(0);

        field.setAccessible(true);
        field.set(dataRequestBatchEventConsumer, actionDataBatchEventPublisher);

        createTestAction();

        actionService.create(new ActionCreateDTO("test", List.of(new StructureParameterCreateDTO("$targetId", "1"),
                new StructureParameterCreateDTO("$amount", "1000"))));

        dataRequestBatchEventConsumer.accept(new ActionDataRequestBatchEvent(new HashSet<>(List.of(new ActionDataRequestEvent(3)))));

        // get batch received by mocked publisher
        final ArgumentCaptor<ActionDataBatchEvent> captor = ArgumentCaptor.forClass(ActionDataBatchEvent.class);
        verify(actionDataBatchEventPublisher).send(captor.capture());

        // verify action and inputs
        var argument = captor.getValue().getBatch().stream().findFirst().get();
        Assertions.assertEquals(3, (long) argument.getActionId());
        Assertions.assertEquals("1", argument.getInput().mustGetByName("$targetId").getData().toString());
        Assertions.assertEquals("1000", argument.getInput().mustGetByName("$amount").getData().toString());

        // verify start node
        var startTest = argument.getNodes().get(0);
        Assertions.assertEquals(startTest.getType(), ActionType.START.getId());
        Assertions.assertEquals(1, (long) startTest.getPosition());
        Assertions.assertEquals("2", startTest.getParameters().mustGetByName("next").getData().toString());

        // verify end node
        var endTest = argument.getNodes().get(1);
        Assertions.assertEquals(endTest.getType(), ActionType.END.getId());
        Assertions.assertEquals(2, (long) endTest.getPosition());
        Assertions.assertTrue(endTest.getParameters().isEmpty());
    }

    @Test
    void actionDeleteEventConsume() {
        createTestAction();
        Map<String, String> properties = new HashMap<>();
        properties.put("$targetId", "1");
        properties.put("$amount", "1000");
        actionService.create(new ActionCreateDTO(new ActionCreateEvent("test", properties)));

        Assertions.assertDoesNotThrow(() -> actionService.findById(3));
        actionDeleteEventConsumer.accept(new ActionDeleteEvent(3));
        Assertions.assertThrows(ResourceNotFoundException.class, () -> actionService.findById(3));
    }

    private void createTestAction()   {
        String name = "test";
        List<String> input = List.of("$targetId", "$amount");
        var start = new NodeCreateDTO(ActionType.START.getId(), 1L, List.of(new StructureParameterCreateDTO("next", "2")));
        var end = new NodeCreateDTO(ActionType.END.getId(), 2L, List.of());
        actionStructureService.create(new ActionStructureCreateDTO(name, input, List.of(start, end)));
    }

}
