package com.example.myapplication.service;

import com.example.myapplication.DBConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:config/application-test.properties")
@Import(DBConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MyServiceTest {

    @Autowired
    private MyService myService;

    @Test
    public void serviceInitTest() throws Exception {
        assertThat(myService).isNotNull();
    }

    @Test
    public void getGreetingTest() throws Exception {
        assertThat(myService.getGreeting()).isEqualTo("Hello! I'm MyAppTest, (app version 0.0.1-TEST).");
    }
}
