package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.domain.Person;
import ru.job4j.chat.domain.Room;
import ru.job4j.chat.repository.RoomRepository;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomRepository roomRepository;

    public RoomController(RoomRepository rooms) {
        this.roomRepository = rooms;
    }

    /**
     * Получить всех комнат
     */

    @GetMapping("/")
    public List<Room> findAll() {
        List<Room> rooms = (List<Room>) roomRepository.findAll();
        return rooms;
    }

    /**
     * Получить всех пользователей в комнате
     */

    @GetMapping("/{id}")
    public List<Person> findAllPersons(@PathVariable int id) {
        Room room = roomRepository.findById(id).get();
        return room.getPersons();
    }

    /**
     * Добавить пользователя в комнату
     *
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> addPerson(@PathVariable int id, @RequestBody Person person) {
        Optional<Room> room = roomRepository.findById(id);
        if (room.isPresent()) {
            room.get().addPerson(person);
            return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Удалить пользователя из комнаты
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable int id, @RequestBody Person person) {
        Optional<Room> room = roomRepository.findById(id);
        if (room.isPresent()) {
            room.get().deletePerson(person);
            return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
