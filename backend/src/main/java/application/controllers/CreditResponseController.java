package application.controllers;

import application.models.CreditRequest;
import application.models.CreditResponse;
import application.repositories.RequestRepository;
import application.repositories.ResponseRepository;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/creditResponse")
public class CreditResponseController {

    private final ResponseRepository responseRepository;
    private final RequestRepository requestRepository;

    public CreditResponseController(ResponseRepository responseRepository, RequestRepository requestRepository) {
        this.responseRepository = responseRepository;
        this.requestRepository = requestRepository;
    }

    @GetMapping()
    public List<CreditResponse> index() {
        return responseRepository.findAll();
    }

    //for sort by status
    @GetMapping("/approved")
    public List<CreditResponse> indexOnlyApproved() {
        return responseRepository.findCreditResponsesByStatus("approved");
    }

    @GetMapping("/{id}")
    public CreditResponse show(@PathVariable("id") int id) {
        return responseRepository.findById(id).get();
    }

    @PostMapping()
    public CreditResponse create(@RequestBody CreditResponse creditResponse) {
        return responseRepository.save(creditResponse);
    }

    //ответ на заявку
    @PostMapping("/{id}")
    public CreditResponse createFromCR(@PathVariable("id") int id) {
        CreditRequest newCreditRequest = requestRepository.findById(id).get();
        CreditResponse creditResponse = new CreditResponse();

        creditResponse.setId_creditresponse(newCreditRequest.getId_creditrequest());
        //creditResponse.setId(newCreditRequest.getId());
        creditResponse.setClient(newCreditRequest.getClient());
        //creditResponse.setPasport(newCreditRequest.getPasport());
        Random random = new Random();
        creditResponse.setSum(random.nextInt(newCreditRequest.getCreditsum()));
        creditResponse.setPeriod(random.nextInt(12)*30);

        if(random.nextBoolean()){
            creditResponse.setStatus("approved");
        }else{
            creditResponse.setStatus("not approved");
        }

        return responseRepository.save(creditResponse);
    }

    @PatchMapping("/{id}")
    public CreditResponse update(@RequestBody CreditResponse creditResponse,
                                 @PathVariable("id") int id) {

        CreditResponse creditResponseToBeUpdated = responseRepository.findById(id).get();

        creditResponseToBeUpdated.setId_creditresponse(creditResponse.getId_creditresponse());
        //creditResponseToBeUpdated.setId(creditResponse.getId());
        creditResponseToBeUpdated.setClient(creditResponse.getClient());
        //creditResponseToBeUpdated.setPasport(creditResponse.getPasport());
        creditResponseToBeUpdated.setPeriod(creditResponse.getPeriod());
        creditResponseToBeUpdated.setSum(creditResponse.getSum());
        creditResponseToBeUpdated.setStatus(creditResponse.getStatus());

        responseRepository.save(creditResponseToBeUpdated);
        return responseRepository.save(creditResponseToBeUpdated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        responseRepository.deleteById(id);
    }
}