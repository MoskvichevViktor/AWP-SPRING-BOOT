package application.controllers;

import application.models.Contract;
import application.models.CreditResponse;
import application.repositories.ContractRepository;
import application.repositories.ResponseRepository;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@Data
@RequestMapping("/allContract")
public class AllContractController {

    private final ContractRepository contractRepository;
    private final ResponseRepository responseRepository;

    public AllContractController(ContractRepository contractRepository, ResponseRepository responseRepository) {
        this.contractRepository = contractRepository;
        this.responseRepository = responseRepository;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("allContract", contractRepository.findAll());
        return "allContract/index";
    }

    //for sort by status
    @GetMapping("/signed")
    public String indexOnlySigned(Model model) {
        model.addAttribute("allContract", contractRepository.findContractsByStatus("signed"));
        return "allContract/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("contract", contractRepository.findById(id).get());
        return "allContract/show";
    }

    @GetMapping("/new")
    public String newContract(@ModelAttribute("contract") Contract contract) {
        return "allContract/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("contract") @Valid Contract contract,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "allContract/new";

        contractRepository.save(contract);
        return "redirect:/allContract";
    }

    //go to the Contract
    @PostMapping("/{id}")
    public String createContract(@PathVariable("id") int id) {
        //переносим в контракт значения из ответа на заявку
        CreditResponse newCreditResponse = responseRepository.findById(id).get();
        Contract contract = new Contract();
        contract.setName(newCreditResponse.getName());
        contract.setPasport(newCreditResponse.getPasport());
        contract.setPeriod(newCreditResponse.getPeriod());
        contract.setSum(newCreditResponse.getSum());
        contract.setStatus("not signed");//defolt
        contractRepository.save(contract);
        return "redirect:/allContract";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("contract", contractRepository.findById(id).get());
        return "allContract/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("contract") @Valid Contract contract, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "allContract/edit";

        Contract contractToBeUpdated = contractRepository.findById(id).get();

        contractToBeUpdated.setName(contract.getName());
        contractToBeUpdated.setPasport(contract.getPasport());
        contractToBeUpdated.setPeriod(contract.getPeriod());
        contractToBeUpdated.setSum(contract.getSum());
        contractToBeUpdated.setStatus(contract.getStatus());

        contractRepository.save(contractToBeUpdated);
        return "redirect:/allContract";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        contractRepository.deleteById(id);
        return "redirect:/allContract";
    }
}