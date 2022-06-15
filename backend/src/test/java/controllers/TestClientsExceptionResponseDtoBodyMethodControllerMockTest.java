package controllers;

import application.AwpApplication;
import application.models.Client;
import application.repositories.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
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
public class TestClientsExceptionResponseDtoBodyMethodControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private ClientRepository clientRepository;

    private final String REQUEST_MAPPING = "/api/v1/clients";

    private Client initAndSaveClient() {
        Client client = new Client();
        client.setId(1l);
        client.setName("name");
        client.setPassport("passport");
        client.setAddress("addres");
        client.setPhone("phone");
        client = clientRepository.save(client);
        return client;
    }

    @Test()
    public void testFindAll() throws Exception {
        Client client = initAndSaveClient();
        mockMvc.perform(MockMvcRequestBuilders
                        .get(REQUEST_MAPPING + "/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(client.getName())))
                .andExpect(jsonPath("$[0].address", is(client.getAddress())))
                .andExpect(jsonPath("$[0].passport", is(client.getPassport())))
                .andExpect(jsonPath("$[0].phone", is(client.getPhone())));

    }

    @Test
    public void testFindById() throws Exception {
        Client client = initAndSaveClient();
        mockMvc.perform(MockMvcRequestBuilders
                        .get(REQUEST_MAPPING + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(client.getName())))
                .andExpect(jsonPath("address", is(client.getAddress())))
                .andExpect(jsonPath("passport", is(client.getPassport())))
                .andExpect(jsonPath("phone", is(client.getPhone())));

    }

    @Test
    public void testUpdate() throws Exception {
        Client client = initAndSaveClient();
        Client updatedClient = client;
        updatedClient.setName("Vasia");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedClient));

        mockMvc.perform(MockMvcRequestBuilders
                        .post(REQUEST_MAPPING + "/update")
                        .content(this.mapper.writeValueAsString(updatedClient))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .get(REQUEST_MAPPING + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name", is(updatedClient.getName())))
                .andExpect(jsonPath("address", is(client.getAddress())))
                .andExpect(jsonPath("passport", is(client.getPassport())))
                .andExpect(jsonPath("phone", is(client.getPhone())));

    }


}
