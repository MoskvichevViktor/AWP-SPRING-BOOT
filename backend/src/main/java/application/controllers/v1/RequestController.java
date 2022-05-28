package application.controllers.v1;

import application.dto.CreditRequestDto;
import application.exception.ResourceNotFoundException;
import application.models.CreditRequest;
import application.services.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/credit_requests")
@AllArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @GetMapping("")
    public List<CreditRequestDto> findAll() {
        return requestService.findAllRequestDto();
    }

    @GetMapping("/{id}")
    public CreditRequestDto findById(@PathVariable Long id) {
        return requestService.findRequestDtoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request with Id: " + id + " not found"));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void create(@RequestBody CreditRequestDto requestDto) {
        requestService.save(requestDto);
    }

    @PutMapping()
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void update(@RequestBody CreditRequestDto requestDto) {
        requestService.update(requestDto);
    }

//    @DeleteMapping("/{id}")
//    public void deleteById(@PathVariable Long id) {
//        requestService.deleteById(id);
//    }

}
