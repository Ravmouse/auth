package ru.job4j.auth.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.auth.domain.Message;
import java.util.List;

/**
 * @author Vitaly Vasilyev, date: 13.09.2020, e-mail: rav.energ@rambler.ru
 * @version 1.0
 */
public interface MessageRepository extends CrudRepository<Message, Integer> {
    List<Message> findMessagesByRoomId(int id);
}