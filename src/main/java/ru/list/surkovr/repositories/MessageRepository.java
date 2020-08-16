package ru.list.surkovr.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.list.surkovr.domain.Message;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

    List<Message> findByTag(String tag);
}
