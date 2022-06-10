package application.controllers.v1;

import application.constants.CreditResponseStatus;
import application.controllers.exception_handler.AbstractAwpExceptionHandlerController;
import application.dto.CreditResponseDto;
import application.dto.CreditResponseInputDto;
import application.exception.AwpException;
import application.services.ResponseService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/credit_responses")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class ResponseController extends AbstractAwpExceptionHandlerController {
    private final ResponseService responseService;

    @GetMapping("")
    public List<CreditResponseDto> findAll(@RequestParam(required = false) CreditResponseStatus status,
                                           @RequestParam(required = false) Long clientId) {
        return responseService.findAll(status, clientId);
    }

    @SneakyThrows
    @GetMapping("/{id}")
    public CreditResponseDto getById(@PathVariable Long id) {
        return responseService.findById(id)
                .orElseThrow(() -> new AwpException("Credit request with id:" + id + " not found"));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void create(@RequestBody CreditResponseInputDto responseDto) {
        responseService.save(responseDto);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void update(@RequestBody CreditResponseDto responseDto) {
        responseService.update(responseDto);
    }

}
