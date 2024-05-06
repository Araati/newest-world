package com.newestworld.executor.executors;

import com.newestworld.commons.model.ModelParameter;
import com.newestworld.commons.model.ModelParameters;
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
class ModifyExecutorTest {

    @Test
    void execute()  {
        ModelParameters parameters = new ModelParameters.Impl(List.of(new ModelParameter(1, "target", 1),
                new ModelParameter(1, "field", "product"),
                new ModelParameter(1, "value", "steel"),
                new ModelParameter(1, "next", 2)));

        ExecutionContext context = new ExecutionContext();
        context.createLocalScope(parameters);

        ActionExecutor executor = new ModifyExecutor();
        String next = executor.exec(context);
        AbstractObjectUpdateEvent event = (AbstractObjectUpdateEvent) context.getEvents().get(0);

        Assertions.assertEquals("2", next);
        Assertions.assertEquals(1, event.getId());
        Assertions.assertEquals("steel", event.getProperties().get("product"));
    }

}
