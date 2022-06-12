package ru.job4j.chat.controller;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.domain.Message;
import ru.job4j.chat.domain.Room;
import ru.job4j.chat.handlers.Operation;
import ru.job4j.chat.service.MessageService;
import ru.job4j.chat.service.RoomService;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

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
    public ResponseEntity<Message> findMessageById(@Valid @PathVariable int id) {
        Optional<Message> message = messageService.findById(id);
        return new ResponseEntity<>(
                message.orElse(new Message()),
                message.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    /**
     * Вывести все сообщения пользователя по id
     */

    @GetMapping("/person/{id}")
    public List<Message> findMessagesByPersonId(@Valid @PathVariable int id) {
        List<Message> messages = (List<Message>) messageService.findByPersonId(id);
        if (messages.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Messages were not found");
        }
        return messages;
    }

    /**
     * Вывести все сообщения комнаты по id
     */

    @GetMapping("/room/{id}")
    public List<Message> findMessagesByRoomId(@Valid @PathVariable int id) {
        List<Message> messages = (List<Message>) messageService.findByRoomId(id);
        if (messages.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Messages were not found");
        }
        return messages;
    }

    /**
     * Вывести сообщения определенного пользователя в
     * определенной комнате
     */

    @GetMapping(value = "/", params = {"rId", "pId"})
    public List<Message> findMessagesByRoomAndPersonId(int rId, int pId) {
        List<Message> messages = (List<Message>) messageService.findByRoomIdAndPersonId(rId, pId);
        if (messages.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Messages were not found");
        }
        return messages;
    }

    /**
     * Опубликовать сообщение в комнате
     */
    @PostMapping("/room/{id}")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity save(@Valid @PathVariable int id, @Valid @RequestBody Message message) {
        Optional<Room> room = roomService.findRoomById(id);
        if (room.isPresent()) {
            message.setRoom(room.get());
            return new ResponseEntity<>(
                    messageService.saveMessage(message),
                    HttpStatus.CREATED
            );
        } else {
            return new ResponseEntity<>(
                    new Message(),
                    HttpStatus.NOT_FOUND
            );
        }

    }

    /**
     * Обновить сообщение
     */
    @PutMapping("/")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Void> update(@Valid @RequestBody Message message) {
        Optional<Message> result = Optional.ofNullable(message);
        if (result.isPresent()) {
            if (message.getDescription() == null) {
                throw new NullPointerException("Message cannot be empty");
            }
            this.messageService.saveMessage(message);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Удалить сообщение
     */
    @DeleteMapping("/{id}")
    @Validated(Operation.OnDelete.class)
    public ResponseEntity<Void> delete(@PathVariable int id) {
        this.messageService.deleteMessage(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/patch/{id}")
    public Message patch(@Valid @PathVariable int id, @Valid @RequestBody Message message) throws InvocationTargetException, IllegalAccessException {
        return messageService.patch(id, message);
    }
}