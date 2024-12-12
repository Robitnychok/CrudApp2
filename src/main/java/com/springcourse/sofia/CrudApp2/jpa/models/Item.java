package com.springcourse.sofia.CrudApp2.jpa.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Item's name should not be empty")
    @Column(name = "item_name")
    @Size(max = 100, message = "Item's name should be lass than 100 characters")
    private String itemName;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public Item(String itemName, Person person) {
        this.itemName = itemName;
        this.owner = person;
    }
    public Item() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotEmpty(message = "Item's name should not be empty") @Size(max = 100, message = "Item's name should be lass than 100 characters") String getItemName() {
        return itemName;
    }

    public void setItemName(@NotEmpty(message = "Item's name should not be empty") @Size(max = 100, message = "Item's name should be lass than 100 characters") String itemName) {
        this.itemName = itemName;
    }

    public Person getPerson() {
        return owner;
    }

    public void setPerson(Person person) {
        this.owner = person;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemName='" + itemName + '\'' +
                ", person=" + owner +
                '}';
    }
}
