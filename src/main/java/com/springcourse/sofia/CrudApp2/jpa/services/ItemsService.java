package com.springcourse.sofia.CrudApp2.jpa.services;

import com.springcourse.sofia.CrudApp2.jpa.models.Item;
import com.springcourse.sofia.CrudApp2.jpa.models.Person;
import com.springcourse.sofia.CrudApp2.jpa.repositories.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ItemsService {

    private ItemsRepository itemsRepository;

    @Autowired
    public ItemsService(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    public List<Item> findByItemName(String itemName){
        return itemsRepository.findByItemName(itemName);
    }

    public List<Item> findByOwner(Person owner){
        return itemsRepository.findByOwner(owner);
    }

}
