package com.newestworld.content.controller.v1;

import com.newestworld.content.ContentApplication;
import com.newestworld.content.dao.AbstractObjectStructureRepository;
import com.newestworld.content.dto.AbstractObjectCreateDTO;
import com.newestworld.content.dto.AbstractObjectStructureCreateDTO;
import com.newestworld.content.model.entity.AbstractObjectStructureEntity;
import com.newestworld.content.service.AbstractObjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
class AbstractObjectApiTest {

    @Autowired
    private AbstractObjectStructureRepository structureRepository;
    @Autowired
    private AbstractObjectService objectService;

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void findById() throws Exception {
        String name = "test";
        Map<String, String> properties = new HashMap<>();
        properties.put("test", "");
        properties.put("test2", "value");

        structureRepository.save(new AbstractObjectStructureEntity(new AbstractObjectStructureCreateDTO(name, properties)));

        properties.put("test", "1000");
        objectService.create(new AbstractObjectCreateDTO(name, properties));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/abstract_object/1");

        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.structureId").value(1))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.properties").value(properties));
    }
}
