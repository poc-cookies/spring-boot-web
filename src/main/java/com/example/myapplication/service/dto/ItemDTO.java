package com.example.myapplication.service.dto;

import lombok.Data;

@Data
public class ItemDTO {

    static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String description;
}
