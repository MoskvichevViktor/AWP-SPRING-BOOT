package application.controllers;


import application.models.Person;
import application.repositories.PersonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonRepository personRepository;

    public PeopleController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * getAllPerson добавлен временно для теста сборки
     */
    @ResponseBody
    @GetMapping("/all")
    public List<Person> getAllPerson() {
        System.out.println(personRepository.findAll());
        return personRepository.findAll();
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personRepository.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personRepository.findById(id).get());
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/new";

        personRepository.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personRepository.findById(id).get());
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "people/edit";

        Person personToBeUpdated = personRepository.findById(id).get();

        personToBeUpdated.setName(person.getName());
        personToBeUpdated.setPasport(person.getPasport());
        personToBeUpdated.setAdress(person.getAdress());
        personToBeUpdated.setPhone(person.getPhone());


        personRepository.save(personToBeUpdated);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personRepository.deleteById(id);
        return "redirect:/people";
    }

    @GetMapping("/serch")
    public String serchByName(@RequestParam("field") String field, @RequestParam("value") String value,
                              Model model) {
        if (field.equals("name")) {
            model.addAttribute("people", personRepository.findPeopleByName(value));
        } else if (field.equals("pasport")) {
            model.addAttribute("people", personRepository.findPeopleByPasport(value));
        } else if (field.equals("phone")) {
            model.addAttribute("people", personRepository.findPeopleByPhone(value));
        }

        return "people/index";
    }
}