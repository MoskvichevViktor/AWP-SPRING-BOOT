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

    @GetMapping("/dto")
    public List<CreditRequestDto> findAllRequestDto(){
        return requestService.findAllRequestDto();
    }

    @GetMapping("/dto/{id}")
    public CreditRequestDto findRequestDtoById(@PathVariable Long id){
        return requestService.findRequestDtoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request with Id: " + id + " not found"));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void create(@RequestBody CreditRequestDto requestDto){
        requestService.save(requestDto);
    }

    @PutMapping()
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void update(@RequestBody CreditRequestDto requestDto){
        requestService.update(requestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        requestService.deleteById(id);
    }

}
