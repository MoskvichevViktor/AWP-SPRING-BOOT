package application.controllers.v1;

import application.services.CreditResponseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/creditResponse")
@AllArgsConstructor
public class CreditResponseController {
    private final CreditResponseService creditResponseService;

}
