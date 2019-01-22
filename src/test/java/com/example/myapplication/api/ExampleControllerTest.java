package com.example.myapplication.api;

import com.example.myapplication.DBConfig;
import com.example.myapplication.aop.ServiceExceptionType;
import com.example.myapplication.service.dto.ItemDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:config/application-test.properties")
@AutoConfigureMockMvc
@Import(DBConfig.class)
public class ExampleControllerTest {

    @Autowired
    private MockMvc mvc;

    // Object->JSON mapping is fair since exactly the same bean is configured for the controller under test
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void baseUrlTest() throws Exception {
        this.mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello! I'm MyAppTest, (app version 0.0.1-TEST)."));
    }

    @Test
    public void itemsTest() throws Exception {
        final String expected = mapper.writeValueAsString(
                Arrays.asList(
                        new ItemDTO(1, "item1", "description1"),
                        new ItemDTO(2, "item2", "description2")));
        this.mvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    public void allItemsTest() throws Exception {
        final String expected = mapper.writeValueAsString(
                Arrays.asList(
                        new ItemDTO(1, "item1", "description1"),
                        new ItemDTO(2, "item2", "description2"),
                        new ItemDTO(3, "item3", "description3")));
        this.mvc.perform(get("/all-items"))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    public void serviceExTest() throws Exception {
        final String error = ServiceExceptionType.UNKNOWN_SERVICE_EXCEPTION.getMessage();
        final String expected = mapper.writeValueAsString(
                new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, error, error));
        this.mvc.perform(get("/service-ex"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().json(expected));
    }
}
