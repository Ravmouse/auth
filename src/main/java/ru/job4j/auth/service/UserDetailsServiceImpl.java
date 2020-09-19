package ru.job4j.auth.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;
import static java.util.Collections.emptyList;

/**
 * @author Vitaly Vasilyev, date: 20.09.2020, e-mail: rav.energ@rambler.ru
 * @version 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private PersonRepository persons;

    public UserDetailsServiceImpl(PersonRepository persons) {
        this.persons = persons;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        final Person person = persons.findPersonByLogin(login);
        if (person == null) {
            throw new UsernameNotFoundException(login);
        }
        return new User(person.getLogin(), person.getPassword(), emptyList());
    }
}