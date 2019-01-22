package com.example.myapplication.api;

import com.example.myapplication.DBConfig;
import com.example.myapplication.aop.ServiceException;
import com.example.myapplication.service.MyService;
import com.example.myapplication.service.dto.ItemDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static com.example.myapplication.aop.ServiceExceptionType.*;

import static org.mockito.BDDMockito.given;
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

    @MockBean
    private MyService myService;

    @Test
    public void baseUrlTest() throws Exception {
        given(myService.getGreeting()).willReturn("Test Greeting!");
        this.mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Test Greeting!"));
    }

    @Test
    public void itemsTest() throws Exception {
        final List<ItemDTO> items = Arrays.asList(
                new ItemDTO(1, "item10", "description10"),
                new ItemDTO(2, "item20", "description20"));
        given(myService.getItemsByIds(Arrays.asList(1, 2))).willReturn(items);
        final String expected = mapper.writeValueAsString(items);
        this.mvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    public void allItemsTest() throws Exception {
        final List<ItemDTO> items = Arrays.asList(
                new ItemDTO(10, "item10", "description10"),
                new ItemDTO(20, "item20", "description20"),
                new ItemDTO(30, "item30", "description30"));
        given(myService.getAllItems()).willReturn(items);
        final String expected = mapper.writeValueAsString(items);
        this.mvc.perform(get("/all-items"))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    public void serviceExTest() throws Exception {
        final String error = UNKNOWN_SERVICE_EXCEPTION.getMessage();
        final String expected = mapper.writeValueAsString(
                new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, error, error));
        given(myService.raiseServiceEx()).willThrow(ServiceException.create(UNKNOWN_SERVICE_EXCEPTION, null));
        this.mvc.perform(get("/service-ex"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().json(expected));
    }
}
