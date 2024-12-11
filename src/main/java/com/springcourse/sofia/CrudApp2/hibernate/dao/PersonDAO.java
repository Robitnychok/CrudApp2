package com.springcourse.sofia.CrudApp2.hibernate.dao;

import com.springcourse.sofia.CrudApp2.hibernate.models.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Component
public class PersonDAO {

    private final EntityManager entityManager;

    @Autowired
    public PersonDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Transactional(readOnly = true)
    public List<Person> index(){
        return entityManager.createQuery("select p from Person p", Person.class)
                .getResultList();
    }

    public Optional<Person> show(String email) {
        try {
            Person person = entityManager
                    .createQuery("select p from Person p where p.email = :email", Person.class)
                    .setParameter("email", email) // Передача параметра
                    .getSingleResult();
            return Optional.of(person);
        } catch (NoResultException e) {
            return Optional.empty(); // Если результат отсутствует
        }
    }


    @Transactional(readOnly = true)
    public Person show(long id) {
        return entityManager.find(Person.class, id);
    }

    @Transactional
    public void save(Person person){
        entityManager.persist(person);
    }

    @Transactional
    public void update(long id, Person updatedPerson){
        Person person = entityManager.find(Person.class, id);
        person.setName(updatedPerson.getName());
        person.setEmail(updatedPerson.getEmail());
        person.setAddress(updatedPerson.getAddress());
    }

    @Transactional
    public void delete(long id){
        Person person = entityManager.find(Person.class, id);
        entityManager.remove(person);
    }

}
