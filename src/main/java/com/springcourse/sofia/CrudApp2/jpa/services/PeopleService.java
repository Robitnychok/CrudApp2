package com.springcourse.sofia.CrudApp2.jpa.services;

import com.springcourse.sofia.CrudApp2.jpa.models.Person;
import com.springcourse.sofia.CrudApp2.jpa.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person findById(long id){
        Optional<Person> findPerson = peopleRepository.findById(id);
        return findPerson.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(long id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(long id) {
        peopleRepository.deleteById(id);
    }

    public void test(){
        System.out.println("Testing with debug inside Hibernate transactional");
    }
}
