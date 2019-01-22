package com.example.myapplication.service;

import com.example.myapplication.DBConfig;
import com.example.myapplication.repository.ItemRepository;
import com.example.myapplication.repository.entity.Item;
import com.example.myapplication.service.dto.ItemDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:config/application-test.properties")
@Import(DBConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MyServiceTest {

    @Autowired
    private MyService myService;

    @MockBean
    private ItemRepository itemRepository;

    @Test
    public void serviceInitTest() throws Exception {
        assertThat(myService).isNotNull();
    }

    @Test
    public void getGreetingTest() throws Exception {
        assertThat(myService.getGreeting()).isEqualTo("Hello! I'm MyAppTest, (app version 0.0.1-TEST).");
    }

    @Test
    public void getItemsTest() throws Exception {
        given(itemRepository.findRelated(Arrays.asList(1, 2)))
                .willReturn(Arrays.asList(
                        new Item(1, "mock item 1", "description1"),
                        new Item(2, "mock item 2", "description2")));
        final List<ItemDTO> expected = Arrays.asList(
                new ItemDTO(1, "mock item 1", "description1"),
                new ItemDTO(2, "mock item 2", "description2"));
        assertThat(myService.getItemsByIds(Arrays.asList(1, 2))).isEqualTo(expected);
    }

    @Test
    public void getAllItemsTest() throws Exception {
        given(itemRepository.findAll())
                .willReturn(Arrays.asList(
                        new Item(97, "mock item 97", "description97"),
                        new Item(98, "mock item 98", "description98"),
                        new Item(99, "mock item 99", "description99")));
        final List<ItemDTO> expected = Arrays.asList(
                new ItemDTO(97, "mock item 97", "description97"),
                new ItemDTO(98, "mock item 98", "description98"),
                new ItemDTO(99, "mock item 99", "description99"));
        assertThat(myService.getAllItems()).isEqualTo(expected);
    }
}
