package com.example.myapplication.api;

import com.example.myapplication.service.MyService;
import com.example.myapplication.aop.ServiceException;
import com.example.myapplication.service.dto.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ExampleController {

    @Autowired
    private MyService myService;

    @GetMapping("/")
    String home() {
        return myService.getGreeting();
    }

    @GetMapping("/items")
    List<ItemDTO> items() throws ServiceException {
        return myService.getItemsByIds(Arrays.asList(1, 2));
    }

    @GetMapping("/all-items")
    List<ItemDTO> allItems() throws ServiceException {
        return myService.getAllItems();
    }

    @GetMapping("/service-ex")
    String serviceEx() throws ServiceException {
        return myService.raiseServiceEx();
    }

    @GetMapping("/ex")
    String ex() throws Exception {
        return myService.raiseEx();
    }
}
