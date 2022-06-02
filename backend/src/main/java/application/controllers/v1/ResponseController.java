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
@AllArgsConstructor
public class ResponseController extends AbstractAwpExceptionHandlerController {
    private final ResponseService responseService;

    @GetMapping("")
    public List<CreditResponse> getAll() {
        return responseService.findAll();
    }

    @SneakyThrows
    @GetMapping("/{id}")
    public CreditResponse getById(@PathVariable Long id) {
        return responseService.
                findById(id).
                orElseThrow(() -> new AwpException("No credit response with Id: " + id));
    }

    @GetMapping("/dto")
    public List<CreditResponseDto> findAllDto() {
        return responseService.findAllResponseDto();
    }

    @SneakyThrows
    @GetMapping("/dto/{id}")
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

//    @DeleteMapping("/{id}")
//    public void deleteById(@PathVariable Long id) {
//        responseService.deleteById(id);
//    }
}
