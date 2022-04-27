package application.controllers;


import application.models.CreditRequest;

import application.repositories.RequestRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/creditRequest")
public class CreditRequestController {

    private final RequestRepository requestRepository;

    public CreditRequestController(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("creditRequests", requestRepository.findAll());
        return "creditRequest/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("creditRequest", requestRepository.findById(id).get());
        return "creditRequest/show";
    }

    @GetMapping("/new")
    public String newcreditRequest(@ModelAttribute("creditRequest") CreditRequest creditRequest) {
        return "creditRequest/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("creditRequest") @Valid CreditRequest creditRequest,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "creditRequest/new";

        requestRepository.save(creditRequest);
        return "redirect:/creditRequest";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("creditRequest", requestRepository.findById(id).get());
        return "creditRequest/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("creditRequest") @Valid CreditRequest creditRequest, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "creditRequest/edit";

        CreditRequest creditRequestToBeUpdated = requestRepository.findById(id).get();

        creditRequestToBeUpdated.setName(creditRequest.getName());
        creditRequestToBeUpdated.setPasport(creditRequest.getPasport());
        creditRequestToBeUpdated.setMaritalstatus(creditRequest.getMaritalstatus());
        creditRequestToBeUpdated.setAdress(creditRequest.getAdress());
        creditRequestToBeUpdated.setPhone(creditRequest.getPhone());
        creditRequestToBeUpdated.setJobdetails(creditRequest.getJobdetails());
        creditRequestToBeUpdated.setCreditsum(creditRequest.getCreditsum());

        requestRepository.save(creditRequestToBeUpdated);
        return "redirect:/creditRequest";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        requestRepository.deleteById(id);
        return "redirect:/creditRequest";
    }
}