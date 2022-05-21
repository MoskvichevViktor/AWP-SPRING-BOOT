package java.controllers;

import application.AwpApplication;
import application.models.Client;
import application.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Класс для Mock тестов контроллеров
 */

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:app_test.properties")
@AutoConfigureMockMvc
@SpringBootTest(classes = AwpApplication.class)
public class PeopleResponseBodyMethodControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    protected UserDetailsService userDetailsService;


    protected UsernamePasswordAuthenticationToken getPrincipal(String username) {

        UserDetails user = this.userDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        user,
                        user.getPassword(),
                        user.getAuthorities());

        return authentication;
    }

    @Test
    public void testFindAllPeople() throws Exception {
        Client client = new Client();
        client.setId(1l);
        client.setName("name");
        client.setPassport("passport");
        client.setAddress("addres");
        client.setPhone("phone");

        client = clientRepository.save(client);
        System.out.println(clientRepository.findAll().toString());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/clients/all")
//                        .principal(getPrincipal("admin"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(client.getName())))
                .andExpect(jsonPath("$[0].address", is(client.getAddress())))
                .andExpect(jsonPath("$[0].passport", is(client.getPassport())))
                .andExpect(jsonPath("$[0].phone", is(client.getPhone())));

    }

}
