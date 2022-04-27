package controllers;

import application.AwpApplication;
import application.models.Person;
import application.repositories.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:app_test.properties")
@AutoConfigureMockMvc
@SpringBootTest(classes = AwpApplication.class)
public class MockitoPeopleResponseBodyMethodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonRepository personRepository;


    @Test
    public void testFindAllPeople() throws Exception {
        Person person = new Person(1,
                "name",
                "pasport",
                "addres",
                "phone");

        person = personRepository.save(person);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/people/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(person.getName())))
                .andExpect(jsonPath("$[0].adress", is(person.getAdress())))
                .andExpect(jsonPath("$[0].pasport", is(person.getPasport())))
                .andExpect(jsonPath("$[0].phone", is(person.getPhone())));

    }

}
