package application.controllers.v1;

import application.exception.ResourceNotFoundException;
import application.models.CreditRequest;
import application.services.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/credit_requests")
@AllArgsConstructor


public class RequestController {
    private final RequestService requestService;

    @GetMapping("/all")
    public List<CreditRequest> getAll() {
        return requestService.findAll();
    }

    @GetMapping("/{id}")
    public CreditRequest getById(@PathVariable Long id) {
        return requestService.
                findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No request with Id: " + id));
    }

}
