package com.newestworld.executor.messaging;

import com.newestworld.commons.model.ActionParameter;
import com.newestworld.commons.model.ActionParameters;
import com.newestworld.commons.model.ActionType;
import com.newestworld.commons.model.BasicAction;
import com.newestworld.executor.ExecutorApplication;
import com.newestworld.executor.dto.BasicActionDTO;
import com.newestworld.executor.service.ActionExecutorAggregator;
import com.newestworld.streams.event.*;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = ExecutorApplication.class, properties = {"spring.profiles.active=test"})
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MessagingTest {

    @Autowired
    private ActionTimeoutBatchEventConsumer timeoutBatchEventConsumer;
    @Autowired
    private EventPublisher<ActionDataRequestBatchEvent> actionDataRequestBatchEventPublisher;
    @Autowired
    private ActionExecutorAggregator aggregator;

    @BeforeEach
    void setUp() throws IllegalAccessException {
        actionDataRequestBatchEventPublisher = mock(EventPublisher.class);
        Field field = ReflectionUtils
                .findFields(ActionTimeoutBatchEventConsumer.class, f -> f.getName().equals("actionDataRequestBatchEventPublisher"),
                        ReflectionUtils.HierarchyTraversalMode.TOP_DOWN)
                .get(0);

        field.setAccessible(true);
        field.set(timeoutBatchEventConsumer, actionDataRequestBatchEventPublisher);
    }

    @Test
    void actionDataBatchEventConsume()  {
        var batch = new ActionTimeoutBatchEvent(List.of(new ActionTimeoutEvent(1), new ActionTimeoutEvent(2)));
        timeoutBatchEventConsumer.accept(batch);

        final ArgumentCaptor<ActionDataRequestBatchEvent> captor = ArgumentCaptor.forClass(ActionDataRequestBatchEvent.class);
        verify(actionDataRequestBatchEventPublisher).send(captor.capture());

        var requestBatch = new ArrayList<>(captor.getValue().getBatch());
        Assertions.assertEquals(1, requestBatch.get(0).getId());
        Assertions.assertEquals(2, requestBatch.get(1).getId());
    }

    // todo: perhaps I should make a separate test for each executor? Making test below seems like hard and useless work
    // I will do it after I make executors send messages after full execution
    @Test
    void actionTimeoutBatchEventConsume()   {
        ActionParameters parameters = new ActionParameters.Impl(List.of(new ActionParameter(1, "$target_id", 1),
                new ActionParameter(1, "$amount", 1000)));
        List<BasicAction> actions = new ArrayList<>(List.of(new BasicActionDTO(1, 1L, ActionType.START, new ActionParameters.Impl(List.of(new ActionParameter(1, "next", 2))), LocalDateTime.now()),
                new BasicActionDTO(1, 2L, ActionType.END, new ActionParameters.Impl(new ArrayList<>(List.of(new ActionParameter(1, "amount", "$amount")))), LocalDateTime.now())));

        aggregator.startExecution(new CompoundActionDataEvent(1L, parameters, actions.stream().map(BasicActionEvent::new).collect(Collectors.toList())));
    }

}
