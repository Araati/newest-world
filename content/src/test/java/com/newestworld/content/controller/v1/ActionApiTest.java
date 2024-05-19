package com.newestworld.content.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.newestworld.commons.exception.ResourceNotFoundException;
import com.newestworld.content.ContentApplication;
import com.newestworld.content.TestData;
import com.newestworld.content.service.ActionService;
import com.newestworld.content.service.ActionStructureService;
import com.newestworld.streams.event.ActionTimeoutCreateEvent;
import com.newestworld.streams.publisher.EventPublisher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Field;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ContentApplication.class, properties = {"spring.profiles.active=test"})
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ActionApiTest {

    @Autowired
    private ActionStructureService actionStructureService;
    @Autowired
    private ActionService actionService;
    @Autowired
    private EventPublisher<ActionTimeoutCreateEvent> actionTimeoutCreateEventPublisher;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @BeforeEach
    void setUp() throws IllegalAccessException {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();

        actionTimeoutCreateEventPublisher = mock(EventPublisher.class);
        Field field = ReflectionUtils
                .findFields(ActionService.class, f -> f.getName().equals("actionTimeoutCreateEventPublisher"),
                        ReflectionUtils.HierarchyTraversalMode.TOP_DOWN)
                .getFirst();

        field.setAccessible(true);
        field.set(actionService, actionTimeoutCreateEventPublisher);
    }

    @Test
    void create() throws Exception {
        actionStructureService.create(TestData.actionStructureCreateDTO);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/action")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(mapper.writeValueAsString(TestData.actionCreateDTO));

        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TestData.expectedActionId))
                .andExpect(jsonPath("$.name").value(TestData.actionStructureName))
                .andExpect(jsonPath("$.structureId").value(TestData.expectedActionStructureId))
                .andExpect(jsonPath("$.timeout").exists())
                // I know that sounds bad... *gunshot*
                // But it refused to work otherwise: https://stackoverflow.com/questions/58306822
                // I use that everywhere
                .andExpect(jsonPath("$.parameters").value(Matchers.equalTo(
                        JsonPath.read(mapper.writeValueAsString(TestData.expectedActionParameters), "$")
                )))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    void delete() throws Exception {
        actionStructureService.create(TestData.actionStructureCreateDTO);
        actionService.create(TestData.actionCreateDTO);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/v1/action/" + TestData.expectedActionId);

        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> actionService.findById(TestData.expectedActionId));
    }

    @Test
    void findById() throws Exception {
        actionStructureService.create(TestData.actionStructureCreateDTO);
        actionService.create(TestData.actionCreateDTO);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/action/" + TestData.expectedActionId);

        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TestData.expectedActionId))
                .andExpect(jsonPath("$.name").value(TestData.actionStructureName))
                .andExpect(jsonPath("$.structureId").value(TestData.expectedActionStructureId))
                .andExpect(jsonPath("$.timeout").exists())
                .andExpect(jsonPath("$.parameters").value(Matchers.equalTo(
                        JsonPath.read(mapper.writeValueAsString(TestData.expectedActionParameters), "$")
                )))
                .andExpect(jsonPath("$.createdAt").exists());
    }
}
