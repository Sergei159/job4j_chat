package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.domain.Room;
import ru.job4j.chat.repository.RoomRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository repository;

    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public Optional<Room> findRoomById(int id) {
        return repository.findById(id);
    }

    public Collection<Room> findAll() {
        return (Collection<Room>) repository.findAll();
    }

    public Room save(Room room) {
        return repository.save(room);
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }
}
