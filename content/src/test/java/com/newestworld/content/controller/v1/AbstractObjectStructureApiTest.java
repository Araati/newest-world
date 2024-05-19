package com.newestworld.content.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.newestworld.commons.exception.ResourceNotFoundException;
import com.newestworld.content.ContentApplication;
import com.newestworld.content.TestData;
import com.newestworld.content.service.AbstractObjectStructureService;
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

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/abstract_object/structure")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(mapper.writeValueAsString(TestData.objectStructureCreateDTO));

        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TestData.expectedObjectStructureId))
                .andExpect(jsonPath("$.name").value(TestData.objectStructureName))
                .andExpect(jsonPath("$.parameters").value(Matchers.equalTo(
                        JsonPath.read(mapper.writeValueAsString(TestData.objectStructureParameters), "$")
                )))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    void delete() throws Exception {
        structureService.create(TestData.objectStructureCreateDTO);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/v1/abstract_object/structure/" + TestData.expectedObjectStructureId);

        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> structureService.findById(TestData.expectedObjectStructureId));
    }

    @Test
    void findById() throws Exception {
        structureService.create(TestData.objectStructureCreateDTO);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/v1/abstract_object/structure/" + TestData.expectedObjectStructureId);

        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TestData.expectedObjectStructureId))
                .andExpect(jsonPath("$.name").value(TestData.objectStructureName))
                .andExpect(jsonPath("$.parameters").value(Matchers.equalTo(
                        JsonPath.read(mapper.writeValueAsString(TestData.objectStructureParameters), "$")
                )))
                .andExpect(jsonPath("$.createdAt").exists());

    }
}
