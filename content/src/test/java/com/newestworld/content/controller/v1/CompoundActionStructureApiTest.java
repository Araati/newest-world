package com.newestworld.content.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.newestworld.commons.exception.ResourceNotFoundException;
import com.newestworld.commons.model.ActionType;
import com.newestworld.content.ContentApplication;
import com.newestworld.content.dto.*;
import com.newestworld.content.service.CompoundActionStructureService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ContentApplication.class, properties = {"spring.profiles.active=test"})
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CompoundActionStructureApiTest {

    @Autowired
    private CompoundActionStructureService actionStructureService;

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void create() throws Exception {
        String name = "test";
        List<String> input = List.of("$targetId", "$amount");
        var start = new BasicActionCreateDTO(ActionType.START.getId(), 1L, List.of(new ActionParamsCreateDTO("next", "2")));
        var end = new BasicActionCreateDTO(ActionType.END.getId(), 2L, List.of());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/compound_action/structure")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(mapper.writeValueAsString(new CompoundActionStructureCreateDTO(name, input, List.of(start, end))));

        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.input").value(Matchers.equalTo(JsonPath.read(mapper.writeValueAsString(input), "$"))))
                .andExpect(jsonPath("$.steps").isArray()) //todo: create a proper check
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    void delete() throws Exception {
        createTestCompound();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/v1/compound_action/structure/1");

        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> actionStructureService.findById(1));
    }

    @Test
    void findById() throws Exception {
        createTestCompound();
        String name = "test";
        List<String> input = List.of("$targetId", "$amount");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/compound_action/structure/1");

        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.input").value(Matchers.equalTo(JsonPath.read(mapper.writeValueAsString(input), "$"))))
                .andExpect(jsonPath("$.steps").isArray()) //todo: create a proper check
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
