package com.example.myapplication.repository;

import com.example.myapplication.DBConfig;
import com.example.myapplication.repository.entity.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(DBConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void repositoryInitTest() throws Exception {
        assertThat(itemRepository).isNotNull();
    }

    @Test
    public void findAllTest() throws Exception {
        final List<Item> expected = Arrays.asList(
                new Item(1, "item1", "description1"),
                new Item(2, "item2", "description2"),
                new Item(3, "item3", "description3"));
        assertThat(itemRepository.findAll()).isEqualTo(expected);
    }

    @Test
    public void findRelatedTest() throws Exception {
        final List<Item> expected = Arrays.asList(
                new Item(1, "item1", "description1"),
                new Item(2, "item2", "description2")
        );
        assertThat(itemRepository.findRelated(Arrays.asList(1, 2))).isEqualTo(expected);
    }
}
