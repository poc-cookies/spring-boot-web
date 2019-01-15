package com.example.myapplication;

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

    public List<Item> getItemsByIds(final List<Integer> ids) throws ServiceException {
        try {
            return this.itemRepository.findRelated(ids);
        } catch (Exception ex) {
            throw new ServiceException("Cannot get items by ids", ex);
        }
    }

    public List<Item> getAllItems() throws ServiceException {
        try {
            return this.itemRepository.findAll();
        } catch (Exception ex) {
            throw new ServiceException("Cannot get all items", ex);
        }
    }

    public String raiseServiceEx() throws ServiceException {
        throw new ServiceException("Service Ex!!!");
    }

    public String raiseEx() throws Exception {
        throw new Exception("Ex!!!");
    }
}
