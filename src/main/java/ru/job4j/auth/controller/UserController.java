package ru.job4j.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Vitaly Vasilyev, date: 20.09.2020, e-mail: rav.energ@rambler.ru
 * @version 1.0
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private PersonRepository persons;
    private BCryptPasswordEncoder encoder;

    public UserController(PersonRepository persons, BCryptPasswordEncoder encoder) {
        this.persons = persons;
        this.encoder = encoder;
    }

    /**
     * curl -X POST -H "Content-Type:application/json" -d "{\"login\":\"user\", \"password\":111, \"roles\":[{\"name\":\"Admin\"}]}" http://localhost:8080/chat/users/sign_up
     */
    @PostMapping("/sign_up")
    public ResponseEntity<Person> signUp(@RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        return new ResponseEntity<>(persons.save(person), HttpStatus.CREATED);
    }

    /**
     * curl -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNjAwNTUwNjQ0fQ.
     * IEUt80ijXdYz2_yHGaA9C2RchkXN_zVdV71FogsSg8vvOjMhUMh4XpL5PveRXuaBaefhaIH600fJnqzf9BXpCA"
     * http://localhost:8080/chat/users/all
     */
    @GetMapping("/all")
    public List<Person> findAll() {
        return StreamSupport.stream(persons.findAll().spliterator(), false).collect(Collectors.toList());
    }
}