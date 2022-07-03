package ru.job4j.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.domain.Person;
import ru.job4j.chat.domain.Role;
import ru.job4j.chat.handlers.Operation;
import ru.job4j.chat.service.PersonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;
    private final BCryptPasswordEncoder encoder;
    private final ObjectMapper objectMapper;

    public PersonController(PersonService personService, BCryptPasswordEncoder encoder, ObjectMapper objectMapper) {
        this.personService = personService;
        this.encoder = encoder;
        this.objectMapper = objectMapper;
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
    public ResponseEntity<Person> findById(@Valid @PathVariable int id) {
        Optional<Person> person = personService.findById(id);
        return new ResponseEntity<>(
                person.orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    /**
     * создать нового пользователя
     */

    @PostMapping("/sign-up")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Person> savePerson(@Valid @RequestBody Person person) {
        Optional<Person> result = Optional.ofNullable(person);
        if (result.isPresent()) {
            if (person.getName().equalsIgnoreCase("admin")) {
                throw new IllegalArgumentException("Username can not be 'admin'");
            }
            if (person.getName().equalsIgnoreCase("user")) {
                throw new IllegalArgumentException("Username can not be 'user'");
            }
            person.setPassword(encoder.encode(person.getPassword()));
            if (person.getRole() == null) {
                person.setRole(Role.of("default role"));
            }
            personService.save(person);
            return new ResponseEntity<>(
                    result.get(),
                    HttpStatus.CREATED
            );
        } else {
            return new ResponseEntity<>(
                    new Person(),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    /**
     * обновить данные пользователя
     */

    @PutMapping("/")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Void> update(@Valid @RequestBody Person person) {
        Optional<Person> result = Optional.ofNullable(person);
        if (result.isPresent()) {
            if (person.getName() == null) {
                throw new NullPointerException("Name cannot be empty");
            }
            if (person.getEmail() == null) {
                throw new NullPointerException("Email cannot be empty");
            }
            personService.save(person);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * удалить пользователя
     */

    @DeleteMapping("/")
    @Validated(Operation.OnDelete.class)
    public ResponseEntity<Void> delete(@Valid @PathVariable int id) {
        personService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/person/patch/{id}")
    public Person patch(@Valid @PathVariable int id, @Valid  @RequestBody Person person) throws InvocationTargetException, IllegalAccessException {
        return personService.patch(id, person);
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", e.getMessage());
            put("type", e.getClass());
        } }));
    }

}
