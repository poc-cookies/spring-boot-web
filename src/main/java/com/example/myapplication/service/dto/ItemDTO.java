package com.example.myapplication.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String description;
}
