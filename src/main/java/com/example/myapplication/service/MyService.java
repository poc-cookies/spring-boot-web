package com.example.myapplication.service;

import com.example.myapplication.AppProperties;
import com.example.myapplication.repository.ItemRepository;
import com.example.myapplication.aop.ServiceException;
import com.example.myapplication.aop.ServiceExceptionType;
import com.example.myapplication.aop.annotation.ServiceMethod;
import com.example.myapplication.service.dto.ItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MyService {

    private final AppProperties appProperties;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public MyService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public String getGreeting() {
        return "Hello! I'm " + this.appProperties.getName() + ", (app version " + this.appProperties.getVersion() + ").";
    }

    @ServiceMethod(exceptionType = ServiceExceptionType.CANNOT_GET_ITEMS_BY_ID)
    public List<ItemDTO> getItemsByIds(final List<Integer> ids) throws ServiceException {
        return this.itemRepository.findRelated(ids).stream()
                .map(item -> modelMapper.map(item, ItemDTO.class))
                .collect(Collectors.toList());
    }

    @ServiceMethod(exceptionType = ServiceExceptionType.CANNOT_GET_ALL_ITEMS)
    public List<ItemDTO> getAllItems() throws ServiceException {
        return this.itemRepository.findAll().stream()
                .map(item -> modelMapper.map(item, ItemDTO.class))
                .collect(Collectors.toList());
    }

    @ServiceMethod
    public String raiseServiceEx() throws ServiceException {
        throw ServiceException.create(ServiceExceptionType.UNKNOWN_SERVICE_EXCEPTION, null);
    }

    public String raiseEx() throws Exception {
        throw new Exception("Ex!!!");
    }
}
