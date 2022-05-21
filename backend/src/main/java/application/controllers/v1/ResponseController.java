package application.controllers.v1;

import application.exception.ResourceNotFoundException;
import application.models.CreditResponse;
import application.services.ResponseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/credit_responses")
@AllArgsConstructor
public class ResponseController {
    private final ResponseService responseService;

    @GetMapping("/all")
    public List<CreditResponse> getAll() {
        return responseService.findAll();
    }

    @GetMapping("/{id}")
    public CreditResponse getById(@PathVariable Long id) {
        return responseService.
                findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No credit response with Id: " + id));
    }
}
