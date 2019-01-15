package com.example.myapplication.repository;

import com.example.myapplication.repository.entity.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends Repository<Item, Integer> {

    List<Item> findAll();

    @Query("select i from item i where i.id in :ids")
    List<Item> findRelated(@Param("ids") List<Integer> ids);
}
