package com.example.myapplication.api;

import com.example.myapplication.Item;
import com.example.myapplication.MyService;
import com.example.myapplication.aop.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ExampleController {

    @Autowired
    private MyService myService;

    @RequestMapping("/")
    String home() {
        return myService.getGreeting();
    }

    @RequestMapping("/items")
    List<Item> items() throws ServiceException {
        return myService.getItemsByIds(Arrays.asList(1, 2));
    }

    @RequestMapping("/all-items")
    List<Item> allItems() throws ServiceException {
        return myService.getAllItems();
    }

    @RequestMapping("/service-ex")
    String serviceEx() throws ServiceException {
        return myService.raiseServiceEx();
    }

    @RequestMapping("/ex")
    String ex() throws Exception {
        return myService.raiseEx();
    }
}
