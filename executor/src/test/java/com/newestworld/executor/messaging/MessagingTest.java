package com.newestworld.executor.messaging;

import com.newestworld.executor.ExecutorApplication;
import com.newestworld.streams.event.*;
import com.newestworld.streams.event.batch.ActionDataRequestBatchEvent;
import com.newestworld.streams.event.batch.ActionTimeoutBatchEvent;
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
import java.util.ArrayList;
import java.util.List;

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

    @BeforeEach
    void setUp() throws IllegalAccessException {
        actionDataRequestBatchEventPublisher = mock(EventPublisher.class);
        Field field = ReflectionUtils
                .findFields(ActionTimeoutBatchEventConsumer.class, f -> f.getName().equals("actionDataRequestBatchEventPublisher"),
                        ReflectionUtils.HierarchyTraversalMode.TOP_DOWN)
                .getFirst();

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

}
