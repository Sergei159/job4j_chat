package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.domain.Person;
import ru.job4j.chat.repository.PersonRepository;
import ru.job4j.chat.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;

    public PersonController(PersonRepository personRepository, RoleRepository roleRepository) {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * отобразить список всех пользователей
     */

    @GetMapping("/")
    public List<Person> findAll() {
        List<Person> persons = (List<Person>) personRepository.findAll();
        for (Person person : persons) {
            person.setRole(roleRepository.findById(person.getRoleId()).get());
        }
        return persons;
    }

    /**
     * отобразить пользователя по его id
     */

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        Optional<Person> person = personRepository.findById(id);
        ResponseEntity responseEntity = new ResponseEntity<>(
                person.orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
        person.get().setRole(roleRepository.findById(
                person.get().getRoleId()).get()
        );
        return responseEntity;
    }

    /**
     * создать нового пользователя
     */

    @PostMapping("/")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        person.setRole(roleRepository.findById(person.getRoleId()).get());
        personRepository.save(person);
        return new ResponseEntity<Person>(
                person,
                HttpStatus.CREATED
        );
    }

    /**
     * обновить данные пользователя
     */

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        personRepository.save(person);
        return ResponseEntity.ok().build();
    }

    /**
     * удалить пользователя
     */

    @DeleteMapping("/")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Person person = personRepository.findById(id).get();
        personRepository.delete(person);
        return ResponseEntity.ok().build();
    }

}
