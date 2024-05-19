package com.newestworld.content.messaging;

import com.newestworld.commons.exception.ValidationFailedException;
import com.newestworld.content.ContentApplication;
import com.newestworld.content.TestData;
import com.newestworld.content.service.AbstractObjectService;
import com.newestworld.content.service.AbstractObjectStructureService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ContentApplication.class, properties = {"spring.profiles.active=test"})
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AbstractObjectMessagingTest {

    @Autowired
    private AbstractObjectStructureService objectStructureService;
    @Autowired
    private AbstractObjectService objectService;

    @Autowired
    private AbstractObjectCreateEventConsumer createEventConsumer;
    @Autowired
    private AbstractObjectUpdateEventConsumer updateEventConsumer;

    @Test
    void abstractObjectCreateEventConsume() {

        objectStructureService.create(TestData.objectStructureCreateDTO);

        createEventConsumer.accept(TestData.objectCreateEvent);
        var object = objectService.findById(TestData.expectedObjectId);

        Assertions.assertEquals(object.getName(), TestData.objectStructureName);
        Assertions.assertEquals(TestData.expectedObjectStructureId, object.getStructureId());
        Assertions.assertEquals(object.getParameters(), TestData.expectedObjectParameters);
    }

    @Test
    void abstractObjectUpdateEventConsume() {

        objectStructureService.create(TestData.objectStructureCreateDTO);
        objectService.create(TestData.objectCreateDTO);

        // Rewrite value check
        updateEventConsumer.accept(TestData.validObjectUpdateEvent);
        Assertions.assertEquals(objectService.findById(TestData.expectedObjectId).getParameters(),
                TestData.expectedObjectUpdateParameters);

        // Update validation check
        Assertions.assertThrows(ValidationFailedException.class, () -> updateEventConsumer.accept(TestData.invalidObjectUpdateEvent));
    }

}
