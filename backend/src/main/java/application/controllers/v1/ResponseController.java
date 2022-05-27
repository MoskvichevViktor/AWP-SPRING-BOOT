package application.controllers.v1;

import application.dto.CreditResponseDto;
import application.exception.ResourceNotFoundException;
import application.models.CreditResponse;
import application.services.ResponseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public List<CreditResponseDto> findAllDto() {
        return responseService.findAllResponseDto();
    }

    @GetMapping("/dto/{id}")
    public CreditResponseDto findDtoById(@PathVariable Long id) {
        return responseService.findResponseDtoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Credit request with id:" + id + " not found"));
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
