package application.controllers.v1;

import application.constants.RequestStatus;
import application.dto.CreditRequestDto;
import application.dto.CreditRequestInputDto;
import application.exception.AwpError;
import application.exception.ResourceNotFoundException;
import application.models.CreditRequest;
import application.services.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/credit_requests")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @GetMapping("")
    public List<CreditRequestDto> findAll(
            @RequestParam(required = false) RequestStatus status
            ) {
        return requestService.findAllRequestDto(status);
    }

    @GetMapping("/{id}")
    public CreditRequestDto findById(@PathVariable Long id) {
        return requestService.findRequestDtoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request with Id: " + id + " not found"));
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public void create(@RequestBody CreditRequestInputDto requestInputDto){
        requestService.save(requestInputDto);
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody CreditRequestInputDto requestInputDto) {
        CreditRequest creditRequest = requestService
                .findById(requestInputDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Request with Id: " + requestInputDto.getId() + " not found"));
        if (!RequestStatus.WAITING.equals(creditRequest.getStatus())) {
            return new ResponseEntity<>(new AwpError("Denied modifying request with id: " + + requestInputDto.getId()), HttpStatus.FORBIDDEN);
        }
        requestService.update(requestInputDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
