package com.springcourse.sofia.CrudApp2.jdbc.controllers;

import com.springcourse.sofia.CrudApp2.jdbc.dao.PersonDAO;
import com.springcourse.sofia.CrudApp2.jdbc.models.Person;
import com.springcourse.sofia.CrudApp2.jdbc.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people1", personDAO.index());
        return ("people/index");
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model){
        model.addAttribute("person", personDAO.show(id));
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
        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors()){
            return ("people/new");
        }
        personDAO.save(person);
        return ("redirect:/people");
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model){
        model.addAttribute("person", personDAO.show(id));
        return ("people/edit");
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") long id){
        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors()){
            return ("people/edit");
        }
        personDAO.update(id, person);
        return ("redirect:/people");
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id){
        personDAO.delete(id);
        return ("redirect:/people");
    }
}
