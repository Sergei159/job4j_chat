package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.domain.Person;
import ru.job4j.chat.domain.Room;
import ru.job4j.chat.service.RoomService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    /**
     * Получить всех комнат
     */

    @GetMapping("/")
    public List<Room> findAll() {
        return (List<Room>) roomService.findAll();
    }

    /**
     * Получить комнату по id
     */

    @GetMapping("/{id}")
    public ResponseEntity<Room> findRoomById(@PathVariable int id) {
        var room = roomService.findRoomById(id);
        return new ResponseEntity<>(
                room.orElse(new Room()),
                room.isPresent() ?  HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    /**
     * обновить комнату
     *
     */
    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Room room) {
        roomService.save(room);
        return ResponseEntity.ok().build();
    }

    /**
     * Удалить комнату
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        this.roomService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
