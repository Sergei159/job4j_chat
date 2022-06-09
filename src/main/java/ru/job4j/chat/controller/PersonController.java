package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.domain.Person;
import ru.job4j.chat.repository.PersonRepository;
import ru.job4j.chat.repository.RoleRepository;
import ru.job4j.chat.service.PersonService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * отобразить список всех пользователей
     */

    @GetMapping("/")
    public List<Person> getAllPersons() {
        return (List<Person>) personService.findAll();
    }

    /**
     * отобразить пользователя по его id
     */

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        Optional<Person> person = personService.findById(id);
        return new ResponseEntity<>(
                person.orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    /**
     * создать нового пользователя
     */

    @PostMapping("/")
    public ResponseEntity<Person> savePerson(@RequestBody Person person) {
        return new ResponseEntity<Person>(
                personService.save(person),
                HttpStatus.CREATED
        );
    }

    /**
     * обновить данные пользователя
     */

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        personService.save(person);
        return ResponseEntity.ok().build();
    }

    /**
     * удалить пользователя
     */

    @DeleteMapping("/")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        personService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
