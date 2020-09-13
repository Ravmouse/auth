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
import ru.job4j.auth.domain.Room;
import ru.job4j.auth.repository.RoomRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Vitaly Vasilyev, date: 13.09.2020, e-mail: rav.energ@rambler.ru
 * @version 1.0
 */
@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomRepository rooms;

    @Autowired
    public RoomController(RoomRepository rooms) {
        this.rooms = rooms;
    }

    /**
     * curl -i http://localhost:8080/chat/rooms
     */
    @GetMapping
    public List<Room> rooms() {
        return StreamSupport.stream(rooms.findAll().spliterator(), false).collect(Collectors.toList());
    }

    /**
     * curl -i http://localhost:8080/chat/rooms/6
     */
    @GetMapping("/{id}")
    public ResponseEntity<Room> roomByName(@PathVariable int id) {
        final Optional<Room> room = rooms.findById(id);
        return new ResponseEntity<>(room.orElse(new Room()), room.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /**
     * curl -X POST -H "Content-Type:application/json" -d "{\"name\":\"Test\"}" http://localhost:8080/chat/rooms
     */
    @PostMapping
    public ResponseEntity<Room> create(@RequestBody Room room) {
        return new ResponseEntity<>(rooms.save(room), HttpStatus.CREATED);
    }

    /**
     * curl -X PUT -H "Content-Type:application/json" -d "{\"id\":\"8\", \"name\":\"Country\"}" http://localhost:8080/chat/rooms
     */
    @PutMapping
    public ResponseEntity<Room> update(@RequestBody Room room) {
        return new ResponseEntity<>(rooms.save(room), HttpStatus.NO_CONTENT);
    }

    /**
     * curl -i -X DELETE http://localhost:8080/chat/rooms/8
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Room> delete(@PathVariable int id) {
        final Room room = new Room(id);
        rooms.delete(room);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}