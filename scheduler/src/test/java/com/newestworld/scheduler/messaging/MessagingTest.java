package com.newestworld.scheduler.messaging;

import com.newestworld.scheduler.SchedulerApplication;
import com.newestworld.scheduler.dao.ActionTimeoutRepository;
import com.newestworld.scheduler.dto.ActionTimeoutCreateDTO;
import com.newestworld.scheduler.model.entity.ActionTimeoutEntity;
import com.newestworld.streams.event.ActionDeleteEvent;
import com.newestworld.streams.event.ActionTimeoutCreateEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

//fixme: how to rewrite env property properly?
@SpringBootTest(classes = SchedulerApplication.class, properties = {"spring.profiles.active=test"})
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MessagingTest {

    @Autowired
    private ActionTimeoutRepository repository;
    @Autowired
    private ActionDeleteEventConsumer actionDeleteEventConsumer;
    @Autowired
    private ActionTimeoutCreateEventConsumer actionTimeoutCreateEventConsumer;
    @Autowired
    private ActionTimeoutBatchEventProducer producer;

    @Test
    void timeoutCreateEventConsume()    {
        actionTimeoutCreateEventConsumer.accept(new ActionTimeoutCreateEvent(1, 0));
        var entity = repository.mustFindByActionId(1);
        Assertions.assertEquals(1, entity.getActionId());
        Assertions.assertTrue(entity.getTimeout() - 1000 < System.currentTimeMillis() && entity.getTimeout() + 1000 > System.currentTimeMillis());
        Assertions.assertFalse(entity.isProcessing());
        Assertions.assertFalse(entity.isDeleted());
    }
    @Test
    void timeoutBatchEventProduce() {
        var event = producer.get();
        Assertions.assertNull(event);

        repository.save(new ActionTimeoutEntity(new ActionTimeoutCreateDTO(1, 0)));
        repository.save(new ActionTimeoutEntity(new ActionTimeoutCreateDTO(2, 0)));
        repository.save(new ActionTimeoutEntity(new ActionTimeoutCreateDTO(3, 100)));
        var batch = new ArrayList<>(producer.get().getBatch());
        Assertions.assertFalse(batch.isEmpty());
        Assertions.assertEquals(2, batch.size());
        Assertions.assertEquals(1, batch.get(0).getId());
        Assertions.assertEquals(2, batch.get(1).getId());

        await().atMost(150, TimeUnit.MILLISECONDS).until(() -> producer.get().getSize() == 3);
    }

    @Test
    void actionDeleteEventConsume()  {
        long actionId = 1;
        repository.save(new ActionTimeoutEntity(new ActionTimeoutCreateDTO(actionId, 1)));
        actionDeleteEventConsumer.accept(new ActionDeleteEvent(actionId));
        Assertions.assertTrue(repository.mustFindByActionId(actionId).isDeleted());

    }
}
