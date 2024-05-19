package com.newestworld.executor.executors;

import com.newestworld.executor.ExecutorApplication;
import com.newestworld.executor.util.ExecutionContext;
import com.newestworld.streams.event.ActionCreateEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

@SpringBootTest(classes = ExecutorApplication.class, properties = {"spring.profiles.active=test"})
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CreateActionExecutorTest {

    @Test
    void execute()  {
        Map<String, String> parameters = Map.of(
                "name", "factory",
                "something", "randomInput",
                "next", "2"
        );

        ExecutionContext context = new ExecutionContext();
        context.createNodeScope(parameters);

        ActionExecutor executor = new CreateActionExecutor();
        String next = executor.exec(context);
        ActionCreateEvent event = (ActionCreateEvent) context.getEvents().getFirst();

        Assertions.assertEquals("2", next);
        Assertions.assertEquals("factory", event.getName());
        Assertions.assertEquals("randomInput", event.getInput().get("something"));
    }

}
