package ru.job4j.auth.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.job4j.auth.domain.Person;
import java.util.List;
import static ru.job4j.auth.config.Fixtures.API;
import static ru.job4j.auth.config.Fixtures.API_ID;

/**
 * @author Vitaly Vasilyev, date: 15.07.2020, e-mail: rav.energ@rambler.ru
 * @version 1.0
 */
@RestController
@RequestMapping("/restapi")
public class RestTemplateController {

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * curl -i http://localhost:8080/person/restapi/get
     * @return список объектов.
     */
    @GetMapping("/get")
    public List<Person> findAll() {
        return restTemplate.exchange(API, HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {}).getBody();
    }

    /**
     * curl -i http://localhost:8080/person/restapi/get/1
     * @param id номер объекта для получения из БД.
     * @return найденный объект.
     */
    @GetMapping("/get/{id}")
    public Person findById(@PathVariable String id) {
        return restTemplate.getForObject(API_ID, Person.class, id);
    }

    /**
     * curl -X POST -H "Content-Type:application/json" -d "{\"login\":\"test\",\"password\":\"test\"}" http://localhost:8080/person/restapi/post
     * @param person объект для добавления в БД.
     * @return добавленный объект.
     */
    @PostMapping("/post")
    public Person create(@RequestBody Person person) {
        return restTemplate.postForObject(API, person, Person.class);
    }

    /**
     * curl -X PUT -H "Content-Type:application/json" -d "{\"id\":\"1\",\"login\":\"tic\",\"password\":\"tac\"}" http://localhost:8080/person/restapi/put
     * @param person объект для редактирования в БД.
     */
    @PutMapping("/put")
    public void update(@RequestBody Person person) {
        restTemplate.put(API, person);
    }

    /**
     * curl -i -X DELETE http://localhost:8080/person/restapi/delete/1
     * @param id номер объекта в БД для удаления.
     */
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        restTemplate.delete(API_ID, id);
    }
}