package com.newestworld.content.messaging;

import com.newestworld.commons.exception.ValidationFailedException;
import com.newestworld.content.ContentApplication;
import com.newestworld.content.dao.AbstractObjectRepository;
import com.newestworld.content.dao.AbstractObjectStructureRepository;
import com.newestworld.content.dto.AbstractObjectCreateDTO;
import com.newestworld.content.dto.AbstractObjectStructureCreateDTO;
import com.newestworld.content.dto.AbstractObjectStructureDTO;
import com.newestworld.content.model.entity.AbstractObjectEntity;
import com.newestworld.content.model.entity.AbstractObjectStructureEntity;
import com.newestworld.streams.event.AbstractObjectCreateEvent;
import com.newestworld.streams.event.AbstractObjectUpdateEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = ContentApplication.class, properties = {"spring.profiles.active=test"})
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AbstractObjectMessagingTest {

    @Autowired
    private AbstractObjectStructureRepository structureRepository;
    @Autowired
    private AbstractObjectRepository objectRepository;

    @Autowired
    private AbstractObjectCreateEventConsumer createEventConsumer;
    @Autowired
    private AbstractObjectUpdateEventConsumer updateEventConsumer;

    @Test
    void abstractObjectCreateEventConsume() {
        String name = "test";
        Map<String, String> properties = new HashMap<>();
        properties.put("test", "");
        properties.put("test2", "value");

        structureRepository.save(new AbstractObjectStructureEntity(new AbstractObjectStructureCreateDTO(name, properties)));

        properties.put("test", "1000");
        createEventConsumer.accept(new AbstractObjectCreateEvent(name, properties));
        var entity = objectRepository.mustFindByIdAndDeletedIsFalse(1);

        Assertions.assertSame(entity.getName(), name);
        Assertions.assertEquals(1, entity.getStructureId());
        Assertions.assertEquals(entity.getProperties(), properties);
    }

    @Test
    void abstractObjectUpdateEventConsume() {
        String name = "test";
        Map<String, String> properties = new HashMap<>();
        properties.put("test", "");
        properties.put("test2", "value");

        var structure = new AbstractObjectStructureEntity(new AbstractObjectStructureCreateDTO(name, properties));
        structureRepository.save(structure);
        properties.put("test", "1000");
        objectRepository.save(new AbstractObjectEntity(new AbstractObjectCreateDTO(name, properties), new AbstractObjectStructureDTO(structure)));

        // Rewrite value check
        properties.put("test2", "newValue");
        updateEventConsumer.accept(new AbstractObjectUpdateEvent(1, properties));
        var entity = objectRepository.mustFindByIdAndDeletedIsFalse(1);
        Assertions.assertEquals(entity.getProperties(), properties);

        // Update validation check
        properties.put("test3", "validationException");
        var event = new AbstractObjectUpdateEvent(1, properties);
        Assertions.assertThrows(ValidationFailedException.class, () -> updateEventConsumer.accept(event));
    }

}
