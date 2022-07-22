package vadim.andreich.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import vadim.andreich.service.BookService;
import vadim.andreich.service.PeopleService;
import vadim.andreich.util.PersonValidator;
import org.springframework.ui.Model;
import vadim.andreich.DAO.PeopleDAO;
import vadim.andreich.models.Person;
import vadim.andreich.DAO.BooksDAO;
import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonValidator personValidator;
    private final PeopleService peopleService;
    private final BookService bookService;

    @Autowired
    public PeopleController(PersonValidator personValidator, PeopleService peopleService, BookService bookService) {

        this.personValidator = personValidator;
        this.peopleService = peopleService;
        this.bookService = bookService;
    }

    @GetMapping()
    public String getAllPeopleListt(Model model, @RequestParam(value = "len") Optional<Integer> len){
        model.addAttribute("people", peopleService.getAll());
        len.ifPresent(System.out::println);
        System.out.println("pusto");
        return "people/allPeople";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id")int id, Model model){
        model.addAttribute("person", peopleService.findById(id).get());
        model.addAttribute("key", id);
        model.addAttribute("books", bookService.findByPerson(id));
        return "people/Person";
    }

    @GetMapping("/addNew")
    public String showAddingPerson(@ModelAttribute("person")Person person){
                return "people/addNew";
    }
    @GetMapping("/{id}/edit")
    public String showEditPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("personToEdit",peopleService.findById(id).get());
        return "people/editPerson";
    }

    @PostMapping("/new")
    public String addPerson(@ModelAttribute("person")@Valid Person person,
                            BindingResult bindingResult){
    personValidator.validate(person,bindingResult);
         if(!bindingResult.hasErrors()) {
             peopleService.addPerson(person);
             return "redirect:/people";
         }
         return "people/addNew";
    }

    @DeleteMapping("/{id}/delete")
    public String deletePerson(@PathVariable("id") int id) {
        peopleService.deletePerson(id);
        return "redirect:/people/";
    }
    @PatchMapping("/{id}")
    public String editPerson(@PathVariable("id")int id, @ModelAttribute("personToEdit")
                            @Valid Person person, BindingResult bindingResult){

        if(!bindingResult.hasErrors()) {
            peopleService.updatePerson(id, person);
            return "redirect:/people";
        }
        return "people/editPerson";
    }
}
