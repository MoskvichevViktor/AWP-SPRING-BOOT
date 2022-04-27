package application.controllers;

import application.models.CreditRequest;
import application.models.CreditResponse;

import application.repositories.RequestRepository;
import application.repositories.ResponseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Random;

@Controller
@RequestMapping("/creditResponse")
public class CreditResponseController {

    private final ResponseRepository responseRepository;
    private final RequestRepository requestRepository;

    public CreditResponseController(ResponseRepository responseRepository, RequestRepository requestRepository) {
        this.responseRepository = responseRepository;
        this.requestRepository = requestRepository;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("creditResponses", responseRepository.findAll());
        return "creditResponse/index";
    }
    //for sort by status
    @GetMapping("/approved")
    public String indexOnlyApproved(Model model) {
        model.addAttribute("creditResponses", responseRepository.findCreditResponsesByStatus("approved"));
        return "creditResponse/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("creditResponse", responseRepository.findById(id).get());
        return "creditResponse/show";
    }

    @GetMapping("/new")
    public String newcreditResponse(@ModelAttribute("creditResponse") CreditResponse creditResponse) {
        return "creditResponse/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("creditResponse") @Valid CreditResponse creditResponse,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "creditResponse/new";

        responseRepository.save(creditResponse);
        return "redirect:/creditResponse";
    }

    //ответ на заявку
    @PostMapping("/{id}")
    public String createFromCR(@PathVariable("id") int id) {
        CreditRequest newCreditRequest = requestRepository.findById(id).get();
        CreditResponse creditResponse = new CreditResponse();
        creditResponse.setIdrequest(newCreditRequest.getId());
        creditResponse.setName(newCreditRequest.getName());
        creditResponse.setPasport(newCreditRequest.getPasport());
        Random random = new Random();
        creditResponse.setSum(random.nextInt(newCreditRequest.getCreditsum()));
        creditResponse.setPeriod(random.nextInt(12)*30);
        if(random.nextBoolean()){
            creditResponse.setStatus("approved");
        }else{
            creditResponse.setStatus("not approved");
        }
        responseRepository.save(creditResponse);
        return "redirect:/creditResponse";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("creditResponse", responseRepository.findById(id).get());
        return "creditResponse/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("creditResponse") @Valid CreditResponse creditResponse, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "creditResponse/edit";

        CreditResponse creditResponseToBeUpdated = responseRepository.findById(id).get();

        creditResponseToBeUpdated.setIdrequest(creditResponse.getIdrequest());
        creditResponseToBeUpdated.setName(creditResponse.getName());
        creditResponseToBeUpdated.setPasport(creditResponse.getPasport());
        creditResponseToBeUpdated.setPeriod(creditResponse.getPeriod());
        creditResponseToBeUpdated.setSum(creditResponse.getSum());
        creditResponseToBeUpdated.setStatus(creditResponse.getStatus());


        responseRepository.save(creditResponseToBeUpdated);
        return "redirect:/creditResponse";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        responseRepository.deleteById(id);
        return "redirect:/creditResponse";
    }
}