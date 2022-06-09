package ru.job4j.chat.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String email;
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public static Person of(String name, String email, String password, Role role) {
        Person person = new Person();
        person.name = name;
        person.email = email;
        person.password = password;
        person.role = role;
        return person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return id == person.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}

