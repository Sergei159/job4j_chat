package ru.job4j.chat.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.domain.Message;

import java.util.Collection;

public interface MessageRepository
        extends CrudRepository<Message, Integer> {

    public Collection<Message> findByRoomId(int roomId);

    public Collection<Message> findByPersonId(int personId);

    public Collection<Message> findByRoomIdAndPersonId(int roomId, int personId);



}
