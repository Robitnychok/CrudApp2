package com.springcourse.sofia.CrudApp2.jpa.controllers;

import com.springcourse.sofia.CrudApp2.jpa.models.Person;
import com.springcourse.sofia.CrudApp2.jpa.services.ItemsService;
import com.springcourse.sofia.CrudApp2.jpa.services.PeopleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final ItemsService itemsService;

    @Autowired
    public PeopleController(PeopleService peopleService, ItemsService itemsService) {
        this.peopleService = peopleService;
        this.itemsService = itemsService;
    }


    @GetMapping()
    public String index(Model model){
        model.addAttribute("people1", peopleService.findAll());

        itemsService.findByItemName("iPhone");
        itemsService.findByOwner(peopleService.findById(9));
        peopleService.test();

        return ("people/index");
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model){
        model.addAttribute("person", peopleService.findById(id));
        return ("people/show");
    }

    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return ("people/new");
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return ("people/new");
        }
        peopleService.save(person);
        return ("redirect:/people");
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model){
        model.addAttribute("person", peopleService.findById(id));
        return ("people/edit");
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") long id){
        if(bindingResult.hasErrors()){
            return ("people/edit");
        }
        peopleService.update(id, person);
        return ("redirect:/people");
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id){
        peopleService.delete(id);
        return ("redirect:/people");
    }
}
