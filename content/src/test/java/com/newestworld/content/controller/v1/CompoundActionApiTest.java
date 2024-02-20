package com.newestworld.content.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.newestworld.commons.exception.ResourceNotFoundException;
import com.newestworld.commons.model.ActionParameter;
import com.newestworld.commons.model.ActionType;
import com.newestworld.content.ContentApplication;
import com.newestworld.content.dto.*;
import com.newestworld.content.service.CompoundActionService;
import com.newestworld.content.service.CompoundActionStructureService;
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
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ContentApplication.class, properties = {"spring.profiles.active=test"})
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CompoundActionApiTest {

    @Autowired
    private CompoundActionStructureService actionStructureService;
    @Autowired
    private CompoundActionService compoundActionService;
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
                .findFields(CompoundActionService.class, f -> f.getName().equals("actionTimeoutCreateEventPublisher"),
                        ReflectionUtils.HierarchyTraversalMode.TOP_DOWN)
                .get(0);

        field.setAccessible(true);
        field.set(compoundActionService, actionTimeoutCreateEventPublisher);
    }

    @Test
    void create() throws Exception {
        createTestCompound();
        List<ActionParameter> input = List.of(new ActionParameter(3, "$targetId", "1"),
                new ActionParameter(3, "$amount", "1000"));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/compound_action")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(mapper.writeValueAsString(new CompoundActionCreateDTO("test", List.of(new ActionParamsCreateDTO("$targetId", "1"),
                        new ActionParamsCreateDTO("$amount", "1000")))));

        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.structureId").exists())
                .andExpect(jsonPath("$.timeout").exists())
                // I know that sounds bad... *gunshot*
                // But it refused to work otherwise: https://stackoverflow.com/questions/58306822
                // I use that everywhere
                .andExpect(jsonPath("$.input").value(Matchers.equalTo(JsonPath.read(mapper.writeValueAsString(input), "$"))))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    void delete() throws Exception {
        createTestCompound();
        compoundActionService.create(new CompoundActionCreateDTO("test", List.of(new ActionParamsCreateDTO("$targetId", "1"),
                new ActionParamsCreateDTO("$amount", "1000"))));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/v1/compound_action/3");

        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> compoundActionService.findById(3));
    }

    @Test
    void findById() throws Exception {
        createTestCompound();
        List<ActionParameter> input = List.of(new ActionParameter(3, "$targetId", "1"),
                new ActionParameter(3, "$amount", "1000"));
        compoundActionService.create(new CompoundActionCreateDTO("test", List.of(new ActionParamsCreateDTO("$targetId", "1"),
                new ActionParamsCreateDTO("$amount", "1000"))));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/compound_action/3");

        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.structureId").exists())
                .andExpect(jsonPath("$.timeout").exists())
                .andExpect(jsonPath("$.input").value(Matchers.equalTo(JsonPath.read(mapper.writeValueAsString(input), "$"))))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    private void createTestCompound()   {
        String name = "test";
        List<String> input = List.of("$targetId", "$amount");
        var start = new BasicActionCreateDTO(ActionType.START.getId(), 1L, List.of(new ActionParamsCreateDTO("next", "2")));
        var end = new BasicActionCreateDTO(ActionType.END.getId(), 2L, List.of());
        actionStructureService.create(new CompoundActionStructureCreateDTO(name, input, List.of(start, end)));
    }

}
