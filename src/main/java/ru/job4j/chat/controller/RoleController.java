package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.domain.Role;
import ru.job4j.chat.service.RoleService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/")
    public List<Role> getAllRoles() {
        List<Role> roles =  (List<Role>) roleService.findAll();
        if (roles.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Roles were not found");
        }
        return roles;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> findRoleById(@PathVariable int id) {
        Optional<Role> role = roleService.findById(id);
        return new ResponseEntity<Role>(
                role.orElse(new Role()),
                role.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Role> save(Role role) {
        return new ResponseEntity<Role>(
                roleService.save(role),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Role role) {
        if (role.getName() == null) {
            throw new NullPointerException("Role name cannot be empty");
        }
        this.roleService.save(role);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        this.roleService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
