package application.controllers;

import application.models.CreditRequest;
import application.repositories.RequestRepository;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/creditRequest")
public class CreditRequestController {

    private final RequestRepository requestRepository;
    public CreditRequestController(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @GetMapping()
    public List<CreditRequest> index() {
        return requestRepository.findAll();
    }

    @GetMapping("/{id}")
    public CreditRequest show(@PathVariable("id") int id) {
        return requestRepository.findById(id).get();
    }

    @PostMapping()
    public CreditRequest create(@RequestBody CreditRequest creditRequest) {
        return requestRepository.save(creditRequest);
    }

    @PatchMapping("/{id}")
    public CreditRequest update(@RequestBody  CreditRequest creditRequest,
                                @PathVariable("id") int id) {

        CreditRequest creditRequestToBeUpdated = requestRepository.findById(id).get();

        creditRequestToBeUpdated.setClient(creditRequest.getClient());
        //creditRequestToBeUpdated.setPasport(creditRequest.getPasport());
        creditRequestToBeUpdated.setMaritalstatus(creditRequest.getMaritalstatus());
        //creditRequestToBeUpdated.setAdress(creditRequest.getAdress());
        //creditRequestToBeUpdated.setPhone(creditRequest.getPhone());
        creditRequestToBeUpdated.setJobdetails(creditRequest.getJobdetails());
        creditRequestToBeUpdated.setCreditsum(creditRequest.getCreditsum());

        return  requestRepository.save(creditRequestToBeUpdated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        requestRepository.deleteById(id);
    }
}