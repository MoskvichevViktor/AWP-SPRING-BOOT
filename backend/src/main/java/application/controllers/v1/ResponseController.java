package application.controllers.v1;

import application.controllers.exception_handler.AbstractAwpExceptionHandlerController;
import application.dto.CreditResponseDto;
import application.exception.AwpException;
import application.models.CreditResponse;
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
    public List<CreditResponseDto> findAllDto() {
        return responseService.findAllResponseDto();
    }

    @SneakyThrows
    @GetMapping("/{id}")
    public CreditResponseDto findDtoById(@PathVariable Long id) {
        return responseService.findResponseDtoById(id)
                .orElseThrow(() -> new AwpException("Credit request with id:" + id + " not found"));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void create(@RequestBody CreditResponseDto responseDto) {
        responseService.save(responseDto);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void update(@RequestBody CreditResponseDto responseDto) {
        responseService.update(responseDto);
    }

}
