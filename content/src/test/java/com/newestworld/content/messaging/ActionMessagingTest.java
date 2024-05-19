package com.newestworld.content.messaging;

import com.newestworld.commons.exception.ResourceNotFoundException;
import com.newestworld.commons.model.Action;
import com.newestworld.content.ContentApplication;
import com.newestworld.content.TestData;
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
                .getFirst();

        field.setAccessible(true);
        field.set(actionService, actionTimeoutCreateEventPublisher);
    }
    @Test
    void actionCreateEventConsume() {
        actionStructureService.create(TestData.actionStructureCreateDTO);

        createEventConsumer.accept(TestData.actionCreateEvent);

        Action action = actionService.findById(TestData.expectedActionId);

        Assertions.assertEquals(TestData.actionStructureName, action.getName());
        Assertions.assertEquals(TestData.expectedActionStructureId, action.getStructureId());
        Assertions.assertEquals(TestData.expectedActionParameters, action.getParameters());
    }

    @Test
    void actionDataRequestBatchEventConsume() throws IllegalAccessException {
        actionDataBatchEventPublisher = mock(EventPublisher.class);
        Field field = ReflectionUtils
                .findFields(ActionDataRequestBatchEventConsumer.class, f -> f.getName().equals("actionDataBatchEventPublisher"),
                        ReflectionUtils.HierarchyTraversalMode.TOP_DOWN)
                .getFirst();

        field.setAccessible(true);
        field.set(dataRequestBatchEventConsumer, actionDataBatchEventPublisher);

        actionStructureService.create(TestData.actionStructureCreateDTO);
        actionService.create(TestData.actionCreateDTO);

        dataRequestBatchEventConsumer.accept(new ActionDataRequestBatchEvent(new HashSet<>(List.of(new ActionDataRequestEvent(TestData.expectedActionId)))));

        // get batch received by mocked publisher
        final ArgumentCaptor<ActionDataBatchEvent> captor = ArgumentCaptor.forClass(ActionDataBatchEvent.class);
        verify(actionDataBatchEventPublisher).send(captor.capture());

        // verify action and inputs
        var argument = captor.getValue().getBatch().stream().findFirst().get();
        Assertions.assertEquals(TestData.expectedActionId, (long) argument.getActionId());
        for (Map.Entry<String, String> entry : TestData.expectedActionParameters.entrySet())   {
            Assertions.assertEquals(entry.getValue(), argument.getInput().mustGetByName(entry.getKey()).getData());
        }

        Assertions.assertEquals(argument.getNodes(), TestData.expectedNodeEvents);
    }


    @Test
    void actionDeleteEventConsume() {
        actionStructureService.create(TestData.actionStructureCreateDTO);
        actionService.create(TestData.actionCreateDTO);

        Assertions.assertDoesNotThrow(() -> actionService.findById(TestData.expectedActionId));
        actionDeleteEventConsumer.accept(new ActionDeleteEvent(TestData.expectedActionId));
        Assertions.assertThrows(ResourceNotFoundException.class, () -> actionService.findById(TestData.expectedActionId));
    }

}
