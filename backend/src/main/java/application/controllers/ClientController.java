package application.controllers;


import application.models.Client;
import application.repositories.ClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * getAllPerson добавлен временно для теста сборки
     */
    @ResponseBody
    @GetMapping("")
    public List<Client> getAllClients() {
        System.out.println(clientRepository.findAll());
        return clientRepository.findAll();
    }

    @GetMapping("/all")
    public String index(Model model) {
        model.addAttribute("people", clientRepository.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", clientRepository.findById(id).get());
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Client client) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Client client,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/new";

        clientRepository.save(client);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", clientRepository.findById(id).get());
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Client client, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "people/edit";

        Client clientToBeUpdated = clientRepository.findById(id).get();

        clientToBeUpdated.setName(client.getName());
        clientToBeUpdated.setPassport(client.getPassport());
        clientToBeUpdated.setAddress(client.getAddress());
        clientToBeUpdated.setPhone(client.getPhone());


        clientRepository.save(clientToBeUpdated);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        clientRepository.deleteById(id);
        return "redirect:/people";
    }

    @GetMapping("/serch")
    public String serchByName(@RequestParam("field") String field, @RequestParam("value") String value,
                              Model model) {
        if (field.equals("name")) {
            model.addAttribute("people", clientRepository.findByName(value));
        } else if (field.equals("pasport")) {
            model.addAttribute("people", clientRepository.findByPassport(value));
        } else if (field.equals("phone")) {
            model.addAttribute("people", clientRepository.findByPhone(value));
        }

        return "people/index";
    }
}