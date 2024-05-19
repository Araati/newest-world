package com.newestworld.executor.executors;

import com.newestworld.executor.ExecutorApplication;
import com.newestworld.executor.util.ExecutionContext;
import com.newestworld.streams.event.AbstractObjectCreateEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

@SpringBootTest(classes = ExecutorApplication.class, properties = {"spring.profiles.active=test"})
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CreateAbstractObjectExecutorTest {

    @Test
    void execute()  {
        String expectedName = "factory";
        String expectedNext = "2";
        Map<String, String> parameters = Map.of(
                "name", expectedName,
                "key1", "value1",
                "key2", "value2",
                "next", expectedNext);
        Map<String, String> expectedParameters = Map.of(
                "key1", "value1",
                "key2", "value2"
        );

        ExecutionContext context = new ExecutionContext();
        context.createNodeScope(parameters);

        ActionExecutor executor = new CreateAbstractObjectExecutor();
        String next = executor.exec(context);
        AbstractObjectCreateEvent event = (AbstractObjectCreateEvent) context.getEvents().getFirst();

        Assertions.assertEquals(expectedNext, next);
        Assertions.assertEquals(expectedName, event.getName());
        Assertions.assertEquals(expectedParameters, event.getProperties());
    }
}
