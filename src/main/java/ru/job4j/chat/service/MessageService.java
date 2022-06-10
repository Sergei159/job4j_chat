package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.domain.Message;
import ru.job4j.chat.domain.Role;
import ru.job4j.chat.repository.MessageRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Optional;

@Service
public class MessageService implements Store {

    private final MessageRepository repository;

    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    public Collection<Message> findAll() {
        return (Collection<Message>) repository.findAll();
    }

    public Optional<Message> findById(int id) {
        return repository.findById(id);
    }

    public Collection<Message> findByRoomId(int roomId) {
        return repository.findByRoomId(roomId);
    }

    public Collection<Message> findByPersonId(int personId) {
        return repository.findByPersonId(personId);
    }

    public Collection<Message> findByRoomIdAndPersonId(int roomId, int personId) {
        return repository.findByRoomIdAndPersonId(roomId, personId);
    }

    public Message saveMessage(Message message) {
        return repository.save(message);
    }

    public void deleteMessage(int id) {
        repository.deleteById(id);
    }

    public Message patch(int id, Message message) throws InvocationTargetException, IllegalAccessException {
        return (Message) patch(repository, id, message);
    }
}
