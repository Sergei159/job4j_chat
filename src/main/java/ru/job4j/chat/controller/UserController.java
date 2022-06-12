package ru.job4j.chat.controller;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.domain.Person;
import ru.job4j.chat.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository users;
    private BCryptPasswordEncoder encoder;

    public UserController(UserRepository users,
                          BCryptPasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody Person person) {
        Optional<Person> result = Optional.ofNullable(person);
        if (result.isPresent()) {
            person.setPassword(encoder.encode(person.getPassword()));
            users.save(person);
        } else {
            throw new IllegalArgumentException("Person in method signUp is null!!!");
        }
    }

    @GetMapping("/all")
    public List<Person> findAll() {
        return users.findAll();
    }
}