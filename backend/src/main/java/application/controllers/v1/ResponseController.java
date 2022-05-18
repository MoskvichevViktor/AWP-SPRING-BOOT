package application.controllers.v1;

import application.models.CreditResponse;
import application.services.ResponseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/creditResponse")
@AllArgsConstructor
public class ResponseController {
    private final ResponseService creditResponseService;

    @GetMapping()
    public List<CreditResponse> index() {
        return creditResponseService.findAll();
    }
}
