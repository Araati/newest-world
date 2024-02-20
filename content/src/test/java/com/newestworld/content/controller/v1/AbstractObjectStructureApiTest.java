package com.newestworld.content.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newestworld.commons.exception.ResourceNotFoundException;
import com.newestworld.content.ContentApplication;
import com.newestworld.content.dto.AbstractObjectStructureCreateDTO;
import com.newestworld.content.service.AbstractObjectStructureService;
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

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ContentApplication.class, properties = {"spring.profiles.active=test"})
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AbstractObjectStructureApiTest {

    @Autowired
    private AbstractObjectStructureService structureService;
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
        Map<String, String> properties = new HashMap<>();
        properties.put("test", "");
        properties.put("test2", "value");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/abstract_object/structure")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(mapper.writeValueAsString(new AbstractObjectStructureCreateDTO(name, properties)));

        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.properties").value(properties))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    void delete() throws Exception {
        String name = "test";
        Map<String, String> properties = new HashMap<>();
        properties.put("test", "");
        properties.put("test2", "value");
        structureService.create(new AbstractObjectStructureCreateDTO(name, properties));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/v1/abstract_object/structure/1");

        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> structureService.findById(1));
    }

    @Test
    void findById() throws Exception {
        String name = "test";
        Map<String, String> properties = new HashMap<>();
        properties.put("test", "");
        properties.put("test2", "value");
        structureService.create(new AbstractObjectStructureCreateDTO(name, properties));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/abstract_object/structure/1");

        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.properties").value(properties))
                .andExpect(jsonPath("$.createdAt").exists());

    }
}
