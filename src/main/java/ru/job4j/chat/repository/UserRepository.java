package ru.job4j.chat.repository;

import org.springframework.stereotype.Component;
import ru.job4j.chat.domain.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserRepository {
    private final ConcurrentHashMap<String, Person> users = new ConcurrentHashMap<>();

    public void save(Person user) {
        users.put(user.getName(), user);
    }


    public Person findByUsername(String username) {
        return users.get(username);
    }

    public List<Person> findAll() {
        return new ArrayList<>(users.values());
    }
}
