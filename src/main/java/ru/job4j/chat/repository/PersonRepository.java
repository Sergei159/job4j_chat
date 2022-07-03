package ru.job4j.chat.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.domain.Person;

import java.util.List;

public interface PersonRepository
        extends CrudRepository<Person, Integer> {

    Person findByName(String name);

    Person save(Person person);

    public List<Person> findAll();

}
