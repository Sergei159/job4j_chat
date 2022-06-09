package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.domain.Person;
import ru.job4j.chat.repository.PersonRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Collection<Person> findAll() {
        return (Collection<Person>) repository.findAll();
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }

    public Person save(Person person) {
        return repository.save(person);
    }

    public Optional<Person> findById(int id) {
        return repository.findById(id);
    }
}
