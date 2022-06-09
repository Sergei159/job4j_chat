package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.domain.Message;
import ru.job4j.chat.service.MessageService;
import ru.job4j.chat.service.RoomService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;
    private final RoomService roomService;

    public MessageController(MessageService messageService, RoomService roomService) {
        this.messageService = messageService;
        this.roomService = roomService;
    }
    /**
     * Вывести все сообщения
     */
    @GetMapping("/")
    public List<Message> findAllMessage() {
        return (List<Message>) messageService.findAll();
    }

    /**
     * Вывести сообщение по id
     */

    @GetMapping("/{id}")
    public ResponseEntity<Message> findMessageById(@PathVariable int id) {
        Optional<Message> message = messageService.findById(id);
        return new ResponseEntity<>(
                message.orElse(new Message()),
                message.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/person/{id}")
    public List<Message> findMessagesByPersonId(@PathVariable int id) {
        return (List<Message>) messageService.findByPersonId(id);
    }

    @GetMapping(value = "/", params = {"rId", "pId"})
    public List<Message> findMessagesByRoomAndPersonId(int rId, int pId) {
        return (List<Message>) messageService.findByRoomIdAndPersonId(rId, pId);
    }

    /**
     * Опубликовать сообщение в комнате
     */
    @PostMapping("/room/{id}")
    public ResponseEntity save(@PathVariable int id, @RequestBody Message message) {
        var room = roomService.findRoomById(id);
        if (room.isPresent()) {
            message.setRoom(room.get());
        }
        return new ResponseEntity<>(
                messageService.saveMessage(message),
                HttpStatus.CREATED
        );
    }

    /**
     * Обновить сообщение
     */
    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Message message) {
        this.messageService.saveMessage(message);
        return ResponseEntity.ok().build();
    }

    /**
     * Удалить сообщение
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        this.messageService.deleteMessage(id);
        return ResponseEntity.ok().build();
    }
}