package com.example.myapplication.service;

import com.example.myapplication.AppProperties;
import com.example.myapplication.repository.entity.Item;
import com.example.myapplication.repository.ItemRepository;
import com.example.myapplication.aop.ServiceException;
import com.example.myapplication.aop.ServiceExceptionType;
import com.example.myapplication.aop.annotation.ServiceMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MyService {

    private final AppProperties appProperties;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    public MyService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public String getGreeting() {
        return "Hello! I'm " + this.appProperties.getName() + ", (app version " + this.appProperties.getVersion() + ").";
    }

    @ServiceMethod(exceptionType = ServiceExceptionType.CANNOT_GET_ITEMS_BY_ID)
    public List<Item> getItemsByIds(final List<Integer> ids) throws ServiceException {
        return this.itemRepository.findRelated(ids);
    }

    @ServiceMethod(exceptionType = ServiceExceptionType.CANNOT_GET_ALL_ITEMS)
    public List<Item> getAllItems() throws ServiceException {
        return this.itemRepository.findAll();
    }

    @ServiceMethod
    public String raiseServiceEx() throws ServiceException {
        throw new ServiceException("Service Exception!", null);
    }

    public String raiseEx() throws Exception {
        throw new Exception("Ex!!!");
    }
}
