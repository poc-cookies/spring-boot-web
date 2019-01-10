package com.example.myapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Application {

    @Autowired
    private MyService myService;

    @RequestMapping("/")
    String home() {
        return myService.getGreeting();
    }

    @RequestMapping("/service-ex")
    String serviceEx() throws ServiceException {
        return myService.raiseServiceEx();
    }

    @RequestMapping("/ex")
    String ex() throws Exception {
        return myService.raiseEx();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
