package com.springcourse.sofia.CrudApp2.jdbc.util;


import com.springcourse.sofia.CrudApp2.jdbc.dao.PersonDAO;
import com.springcourse.sofia.CrudApp2.jdbc.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO){
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if(personDAO.show(person.getEmail()).isPresent())
            errors.rejectValue("email", "", "Email is already in use");
    }
}
