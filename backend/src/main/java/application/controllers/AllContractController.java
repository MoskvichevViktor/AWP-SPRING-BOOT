package application.controllers;

import application.models.Contract;
import application.models.CreditResponse;
import application.repositories.ContractRepository;
import application.repositories.ResponseRepository;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/allContract")
public class AllContractController {

    private final ContractRepository contractRepository;
    private final ResponseRepository responseRepository;

    public AllContractController(ContractRepository contractRepository, ResponseRepository responseRepository) {
        this.contractRepository = contractRepository;
        this.responseRepository = responseRepository;
    }

    @GetMapping()
    public List<Contract> index() {
       //возращаем лист контрактов
        return contractRepository.findAll();
    }

    //for sort by status
    @GetMapping("/signed")
    public List<Contract> indexOnlySigned() {

        return contractRepository.findContractsByStatus("signed");
    }

    @GetMapping("/{id}")
    public Contract show(@PathVariable("id") int id) {

        return contractRepository.findById(id).get();
    }

    @PostMapping()
    public Contract create(@RequestBody  Contract contract) {
        return contractRepository.save(contract);
    }

    //go to the Contract
   @PostMapping("/{id}")
    public Contract createContract(@PathVariable("id") int id) {
        //переносим в контракт значения из ответа на заявку
        CreditResponse newCreditResponse = responseRepository.findById(id).get();
        Contract contract = new Contract();
        contract.setClient(newCreditResponse.getClient());
        contract.setPeriod(newCreditResponse.getPeriod());
        contract.setSum(newCreditResponse.getSum());
        contract.setStatus("not signed");//defolt

        return contractRepository.save(contract);
    }


    @PatchMapping("/{id}")
    public Contract update(@RequestBody Contract contract,
                           @PathVariable("id") int id) {

        Contract contractToBeUpdated = contractRepository.findById(id).get();

        contractToBeUpdated.setClient(contract.getClient());
        contractToBeUpdated.setPeriod(contract.getPeriod());
        contractToBeUpdated.setSum(contract.getSum());
        contractToBeUpdated.setStatus(contract.getStatus());

        return contractRepository.save(contractToBeUpdated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        contractRepository.deleteById(id);
    }
}