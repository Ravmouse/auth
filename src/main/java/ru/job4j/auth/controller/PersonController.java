package ru.job4j.auth.controller;

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
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Vitaly Vasilyev, date: 29.06.2020, e-mail: rav.energ@rambler.ru
 * @version 1.0
 */
@RestController
@RequestMapping("/persons")
public class PersonController {
    private final PersonRepository persons;

    public PersonController(PersonRepository persons) {
        this.persons = persons;
    }

    /**
     * curl -i http://localhost:8080/chat/persons
     */
    @GetMapping
    public List<Person> persons() {
        return StreamSupport.stream(persons.findAll().spliterator(), false).collect(Collectors.toList());
    }

    /**
     * curl -i http://localhost:8080/chat/persons/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        final Optional<Person> person = persons.findById(id);
        return new ResponseEntity<>(person.orElse(new Person()), person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /**
     * curl -X POST -H "Content-Type:application/json" -d "{\"login\":\"abc\", \"password\":333, \"roles\":[{\"name\":\"Admin\"}]}" http://localhost:8080/chat/persons
     */
    @PostMapping
    public ResponseEntity<Person> create(@RequestBody Person person) {
        return new ResponseEntity<>(persons.save(person), HttpStatus.CREATED);
    }

    /**
     * curl -X PUT -H "Content-Type:application/json" -d "{\"id\":\"5\", \"login\":\"bill\", \"password\":678}" http://localhost:8080/chat/persons
     */
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody Person person) {
        persons.save(person);
        return ResponseEntity.ok().build();
    }

    /**
     * curl -i -X DELETE http://localhost:8080/chat/persons/2
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        persons.deleteById(id);
        return ResponseEntity.ok().build();
    }
}