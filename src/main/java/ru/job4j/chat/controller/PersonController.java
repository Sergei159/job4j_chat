package ru.job4j.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.domain.Person;
import ru.job4j.chat.service.PersonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @PostMapping("/sign-up")
    public ResponseEntity<Person> savePerson(@RequestBody Person person) {
        if (person.getName().equalsIgnoreCase("admin")) {
            throw new IllegalArgumentException("Username can not be 'admin'");
        }
        if (person.getName().equalsIgnoreCase("user")) {
            throw new IllegalArgumentException("Username can not be 'user'");
        }
        person.setPassword(encoder.encode(person.getPassword()));
        return new ResponseEntity<>(
                personService.save(person),
                HttpStatus.CREATED
        );
    }

    /**
     * обновить данные пользователя
     */

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        if (person.getName() == null) {
            throw new NullPointerException("Name cannot be empty");
        }
        if (person.getEmail() == null) {
            throw new NullPointerException("Email cannot be empty");
        }
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
