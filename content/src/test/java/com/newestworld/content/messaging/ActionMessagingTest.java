package com.newestworld.content.messaging;

import com.newestworld.commons.exception.ResourceNotFoundException;
import com.newestworld.commons.model.ActionType;
import com.newestworld.commons.model.CompoundAction;
import com.newestworld.content.ContentApplication;
import com.newestworld.content.dto.ModelParameterCreateDTO;
import com.newestworld.content.dto.NodeCreateDTO;
import com.newestworld.content.dto.CompoundActionCreateDTO;
import com.newestworld.content.dto.CompoundActionStructureCreateDTO;
import com.newestworld.content.service.CompoundActionService;
import com.newestworld.content.service.CompoundActionStructureService;
import com.newestworld.streams.event.*;
import com.newestworld.streams.event.batch.ActionDataRequestBatchEvent;
import com.newestworld.streams.event.batch.CompoundActionDataBatchEvent;
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
    private CompoundActionStructureService actionStructureService;
    @Autowired
    private CompoundActionService compoundActionService;

    @Autowired
    private ActionCreateEventConsumer createEventConsumer;
    @Autowired
    private ActionDataRequestBatchEventConsumer dataRequestBatchEventConsumer;
    @Autowired
    private ActionDeleteEventConsumer actionDeleteEventConsumer;

    @Autowired
    private EventPublisher<ActionTimeoutCreateEvent> actionTimeoutCreateEventPublisher;
    @Autowired
    private EventPublisher<CompoundActionDataBatchEvent> actionDataBatchEventPublisher;

    @BeforeEach
    void setUp() throws IllegalAccessException {
        actionTimeoutCreateEventPublisher = mock(EventPublisher.class);
        Field field = ReflectionUtils
                .findFields(CompoundActionService.class, f -> f.getName().equals("actionTimeoutCreateEventPublisher"),
                        ReflectionUtils.HierarchyTraversalMode.TOP_DOWN)
                .get(0);

        field.setAccessible(true);
        field.set(compoundActionService, actionTimeoutCreateEventPublisher);
    }
    @Test
    void actionCreateEventConsume() {

        createTestCompound();

        Map<String, String> properties = new HashMap<>();
        properties.put("$targetId", "1");
        properties.put("$amount", "1000");
        createEventConsumer.accept(new CompoundActionCreateEvent("test", properties));

        CompoundAction compoundAction = compoundActionService.findById(3);
        Assertions.assertSame("test", compoundAction.getName());
        Assertions.assertEquals(1, compoundAction.getStructureId());
        Assertions.assertEquals("1", compoundAction.getInput().mustGetByName("$targetId").getValue());
        Assertions.assertEquals("1000", compoundAction.getInput().mustGetByName("$amount").getValue().toString());
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

        createTestCompound();

        compoundActionService.create(new CompoundActionCreateDTO("test", List.of(new ModelParameterCreateDTO("$targetId", "1"),
                new ModelParameterCreateDTO("$amount", "1000"))));

        dataRequestBatchEventConsumer.accept(new ActionDataRequestBatchEvent(new HashSet<>(List.of(new ActionDataRequestEvent(3)))));

        // get batch received by mocked publisher
        final ArgumentCaptor<CompoundActionDataBatchEvent> captor = ArgumentCaptor.forClass(CompoundActionDataBatchEvent.class);
        verify(actionDataBatchEventPublisher).send(captor.capture());

        // verify action and inputs
        var argument = captor.getValue().getBatch().stream().findFirst().get();
        Assertions.assertEquals(3, (long) argument.getActionId());
        Assertions.assertEquals("1", argument.getInput().mustGetByName("$targetId").getData().toString());
        Assertions.assertEquals("1000", argument.getInput().mustGetByName("$amount").getData().toString());

        // verify start node
        var startTest = argument.getNodes().get(0);
        Assertions.assertEquals(startTest.getType(), ActionType.START.getId());
        Assertions.assertEquals(1, (long) startTest.getOrder());
        Assertions.assertEquals("2", startTest.getParameters().mustGetByName("next").getData().toString());

        // verify end node
        var endTest = argument.getNodes().get(1);
        Assertions.assertEquals(endTest.getType(), ActionType.END.getId());
        Assertions.assertEquals(2, (long) endTest.getOrder());
        Assertions.assertTrue(endTest.getParameters().isEmpty());
    }

    @Test
    void actionDeleteEventConsume() {
        createTestCompound();
        Map<String, String> properties = new HashMap<>();
        properties.put("$targetId", "1");
        properties.put("$amount", "1000");
        compoundActionService.create(new CompoundActionCreateDTO(new CompoundActionCreateEvent("test", properties)));

        Assertions.assertDoesNotThrow(() -> compoundActionService.findById(3));
        actionDeleteEventConsumer.accept(new ActionDeleteEvent(3));
        Assertions.assertThrows(ResourceNotFoundException.class, () -> compoundActionService.findById(3));
    }

    private void createTestCompound()   {
        String name = "test";
        List<String> input = List.of("$targetId", "$amount");
        var start = new NodeCreateDTO(ActionType.START.getId(), 1L, List.of(new ModelParameterCreateDTO("next", "2")));
        var end = new NodeCreateDTO(ActionType.END.getId(), 2L, List.of());
        actionStructureService.create(new CompoundActionStructureCreateDTO(name, input, List.of(start, end)));
    }

}
