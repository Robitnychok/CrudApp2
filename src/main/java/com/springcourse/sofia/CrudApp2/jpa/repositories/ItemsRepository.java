package com.springcourse.sofia.CrudApp2.jpa.repositories;

import com.springcourse.sofia.CrudApp2.jpa.models.Item;
import com.springcourse.sofia.CrudApp2.jpa.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Item, Long> {

    List<Item> findByOwner(Person owner);
    List<Item> findByItemName(String itemName);
}
