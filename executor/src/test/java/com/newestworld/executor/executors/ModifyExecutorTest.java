package com.newestworld.executor.executors;

import com.newestworld.commons.model.ActionParameter;
import com.newestworld.commons.model.ActionParameters;
import com.newestworld.executor.ExecutorApplication;
import com.newestworld.executor.util.ExecutionContext;
import com.newestworld.streams.event.AbstractObjectUpdateEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest(classes = ExecutorApplication.class, properties = {"spring.profiles.active=test"})
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ModifyExecutorTest {

    @Test
    void execute()  {
        ActionParameters parameters = new ActionParameters.Impl(List.of(new ActionParameter(1, "target", 1),
                new ActionParameter(1, "field", "product"),
                new ActionParameter(1, "value", "steel"),
                new ActionParameter(1, "next", 2)));

        ExecutionContext context = new ExecutionContext();
        context.createLocalScope(parameters);

        ActionExecutor executor = new ModifyExecutor();
        String next = executor.exec(context);
        AbstractObjectUpdateEvent event = (AbstractObjectUpdateEvent) context.getEvents().get(0);

        Assertions.assertEquals("2", next);
        Assertions.assertEquals(1, event.getId());
        Assertions.assertEquals("steel", event.getProperties().get("product").toString());
    }

}
