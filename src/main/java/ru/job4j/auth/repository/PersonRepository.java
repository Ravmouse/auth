package ru.job4j.auth.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.auth.domain.Person;

/**
 * @author Vitaly Vasilyev, date: 29.06.2020, e-mail: rav.energ@rambler.ru
 * @version 1.0
 */
public interface PersonRepository extends CrudRepository<Person, Integer> {
    Person findPersonByLogin(String login);
}