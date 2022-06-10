package ru.job4j.chat.domain;

import ru.job4j.chat.handlers.Operation;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Id must not be null",
            groups = {Operation.OnUpdate.class, Operation.OnDelete.class})
    private int id;

    @Size(min = 2, message = "Name must have at least 2 symbols")
    private String name;

    @Email
    private String email;

    @NotBlank(message = "Message body must contain symbols")
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

