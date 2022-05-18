package application.controllers.v1;

import application.models.Client;
import application.services.ResponseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/creditRequest")
@AllArgsConstructor


public class RequestController {
    private final ResponseService ResponseService;

    @GetMapping("/all")
    public List<Client> getAllClients() {
        return null;
    }

}
