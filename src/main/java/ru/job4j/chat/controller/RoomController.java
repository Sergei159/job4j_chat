package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.domain.Role;
import ru.job4j.chat.domain.Room;
import ru.job4j.chat.handlers.Operation;
import ru.job4j.chat.service.RoomService;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
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
        List<Room> rooms =  (List<Room>) roomService.findAll();
        if (rooms.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rooms were not found");
        }
        return rooms;
    }

    /**
     * Получить комнату по id
     */

    @GetMapping("/{id}")
    public ResponseEntity<Room> findRoomById(@Valid @PathVariable int id) {
        var room = roomService.findRoomById(id);
        return new ResponseEntity<>(
                room.orElse(new Room()),
                room.isPresent() ?  HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    /**
     * создать комнату
     *
     */

    @PostMapping("/")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Room> save(@Valid @RequestBody Room room) {
        Optional<Room> result = Optional.ofNullable(roomService.save(room));
        return new ResponseEntity<>(
                result.orElse(new Room()),
                result.isPresent() ? HttpStatus.CREATED : HttpStatus.NOT_FOUND
        );
    }

    /**
     * обновить комнату
     *
     */
    @PutMapping("/")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Void> update(@Valid @RequestBody Room room) {
        Optional<Room> result = Optional.ofNullable(room);
        if (result.isPresent()) {
            if (room.getName() == null) {
                throw new NullPointerException("Room name cannot be empty");
            }
            roomService.save(room);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Удалить комнату
     */
    @DeleteMapping("/{id}")
    @Validated(Operation.OnDelete.class)
    public ResponseEntity<Void> delete(@Valid @PathVariable int id) {
        this.roomService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/patch/{id}")
    public Room patch(@Valid @PathVariable int id, @Valid @RequestBody Room room) throws InvocationTargetException, IllegalAccessException {
        return roomService.patch(id, room);
    }
}

