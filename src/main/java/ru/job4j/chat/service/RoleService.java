package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.domain.Role;
import ru.job4j.chat.domain.Room;
import ru.job4j.chat.repository.RoleRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Optional;

@Service
public class RoleService implements Store {

    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public Collection<Role> findAll() {
        return (Collection<Role>) repository.findAll();
    }

    public Optional<Role> findById(int id) {
        return repository.findById(id);
    }

    public Role save(Role role) {
        return repository.save(role);
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }

    public Role patch(int id, Role role) throws InvocationTargetException, IllegalAccessException {
        return (Role) patch(repository, id, role);
    }
}
