package application.controllers.v1;

import application.services.ResponseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/creditRequest")
@AllArgsConstructor


public class RequestController {
    private final ResponseService creditResponseService;


}
