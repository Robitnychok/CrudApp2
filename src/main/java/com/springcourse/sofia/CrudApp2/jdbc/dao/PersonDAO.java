package com.springcourse.sofia.CrudApp2.jdbc.dao;

import com.springcourse.sofia.CrudApp2.jdbc.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM Person",new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> show(String email){
        return jdbcTemplate.query("SELECT * FROM Person WHERE email=?", new Object[]{email}, new BeanPropertyRowMapper<>(Person.class)).
                stream().findAny();
    }

    public Person show(long id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).
                stream().findAny().orElse(null);
    }

    public void save(Person person){
        jdbcTemplate.update("INSERT INTO Person (name, age, email, address) VALUES (?, ?, ?, ?)",
                person.getName(), person.getAge(), person.getEmail(), person.getAddress());
    }

    public void update(long id, Person updatedPerson){
        jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=?, adress=? WHERE id=?", updatedPerson.getName(), updatedPerson.getAge(),updatedPerson.getEmail(), updatedPerson.getAddress(), id);
    }

    public void delete(long id){
        jdbcTemplate.update("DELETE FROM Person WHERE id=?",id);
    }

    /////////////////////////////////////////////////////////////////
    ///////                Batch Update
    /////////////////////////////////////////////////////////////////

    public void testMultipleUpdate(){
        List<Person> people = create1000People();

        long before = System.currentTimeMillis();

        for(Person person : people){
            jdbcTemplate.update("INSERT INTO Person (id, name, age, email) VALUES (?, ?, ?, ?)",
                    person.getId(), person.getName(), person.getAge(), person.getEmail());
        }

        long after = System.currentTimeMillis();
        System.out.println("Time " + (after - before));
    }

    public void testBatchUpdate(){
        List<Person> people = create1000People();

        long before = System.currentTimeMillis();

        jdbcTemplate.batchUpdate("INSERT INTO Person VALUES (?, ?, ?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, people.get(i).getId());
                ps.setString(2, people.get(i).getName());
                ps.setInt(3, people.get(i).getAge());
                ps.setString(4, people.get(i).getEmail());
            }

            @Override
            public int getBatchSize() {
                return people.size();
            }
        });

        long after = System.currentTimeMillis();
        System.out.println("Time " + (after - before));
    }

    private List<Person> create1000People() {
        List<Person> people = new ArrayList<>();

        int a = 0;
        while (a < 1000) {
            people.add(new Person(a, "I " + a, 1, "i" + a + "@gmail.com" + a, " + address"));
            a++;
        }

        return people;
    }

}
