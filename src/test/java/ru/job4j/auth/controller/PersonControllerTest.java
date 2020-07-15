package ru.job4j.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToCompressingWhiteSpace;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author Vitaly Vasilyev, date: 01.07.2020, e-mail: rav.energ@rambler.ru
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonRepository repository;

    private List<Person> persons;

    @Before
    public void initialize() {
        persons = Lists.newArrayList(new Person("parsentev", "123"), new Person("ban", "123"), new Person("ivan", "123"));
    }

    @Test
    public void findAllTest() throws Exception {
        given(repository.findAll()).willReturn(persons);

        mvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(persons)));
        verify(repository, times(1)).findAll();
    }

    @Test
    public void findByIdTest() throws Exception {
        given(repository.findById(3)).willReturn(Optional.ofNullable(persons.get(2)));

        mvc.perform(get("/3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.login").value("ivan"))
                .andExpect(jsonPath("$.password").value("123"));
    }

    @Test
    public void createTest() throws Exception {
        final Person person = persons.get(2);
        given(repository.save(new Person("ivan", "123"))).willReturn(person);

        mvc.perform(post("/")
                .param("login", "ivan").param("password", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person)))
                .andDo(print())
                .andExpect(status().isCreated());

        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
        verify(repository, times(1)).save(argument.capture());
        assertThat(argument.getValue().toString(), is(equalToCompressingWhiteSpace(persons.get(2).toString())));
    }

    @Test
    public void updateTest() throws Exception {
        final Person person = persons.get(1);
        given(repository.save(new Person("ban", "123"))).willReturn(person);

        mvc.perform(put("/")
                .param("login", "ban").param("password", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person)))
                .andDo(print())
                .andExpect(status().isOk());

        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
        verify(repository, times(1)).save(argument.capture());
        assertThat(argument.getValue().toString(), is(equalToCompressingWhiteSpace(persons.get(1).toString())));
    }

    @Test
    public void deleteTest() throws Exception {
        final Person person = new Person();
        person.setId(1);
        mvc.perform(delete("/1"))
                .andDo(print())
                .andExpect(status().isOk());

        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
        verify(repository, times(1)).delete(argument.capture());
        assertThat(argument.getValue().toString(), is(equalToCompressingWhiteSpace(person.toString())));
    }
}