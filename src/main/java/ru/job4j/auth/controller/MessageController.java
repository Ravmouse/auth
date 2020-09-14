package ru.job4j.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.auth.domain.Message;
import ru.job4j.auth.domain.Room;
import ru.job4j.auth.repository.MessageRepository;
import java.util.List;

/**
 * @author Vitaly Vasilyev, date: 13.09.2020, e-mail: rav.energ@rambler.ru
 * @version 1.0
 */
@RestController
@RequestMapping("/rooms/{roomId}/messages")
public class MessageController {

    private final MessageRepository messages;

    @Autowired
    public MessageController(MessageRepository messages) {
        this.messages = messages;
    }

    /**
     * curl -i http://localhost:8080/chat/rooms/1/messages
     */
    @GetMapping
    public List<Message> messages(@PathVariable int roomId) {
        return messages.findMessagesByRoomId(roomId);
    }

    /**
     * curl -i http://localhost:8080/chat/rooms/1/messages/1
     */
    @GetMapping("/{messageId}")
    public ResponseEntity<Message> messageById(@PathVariable int messageId) {
        final Message messageById = messages
                .findById(messageId)
                .orElse(new Message());
        return new ResponseEntity<>(messageById, HttpStatus.OK);
    }

    /**
     * curl -X POST -H "Content-Type:application/json" -d "{\"message\":\"Apple\", \"person\":{\"id\":1}}" http://localhost:8080/chat/rooms/3/messages
     */
    @PostMapping
    public ResponseEntity<Message> create(@PathVariable int roomId, @RequestBody Message message) {
        message.setRoom(new Room(roomId));
        messages.save(message);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    /**
     * curl -X PUT -H "Content-Type:application/json" -d "{\"id\":7, \"message\":\"Crazy\"}" http://localhost:8080/chat/rooms/7/messages
     */
    @PutMapping
    public ResponseEntity<Message> update(@RequestBody Message message) {
        final Message found = messages
                .findById(message.getId())
                .orElse(new Message());
        found.setMessage(message.getMessage());
        messages.save(found);
        return new ResponseEntity<>(found, HttpStatus.NO_CONTENT);
    }

    /**
     * curl -i -X DELETE http://localhost:8080/chat/rooms/7/messages/15
     */
    @DeleteMapping("/{messageId}")
    public ResponseEntity<Message> delete(@PathVariable int messageId) {
        messages.deleteById(messageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}