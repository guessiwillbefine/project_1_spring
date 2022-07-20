package vadim.andreich.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import vadim.andreich.util.PersonValidator;
import org.springframework.ui.Model;
import vadim.andreich.DAO.PeopleDAO;
import vadim.andreich.models.Person;
import vadim.andreich.DAO.BooksDAO;
import javax.validation.Valid;


@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleDAO peopleDAO;
    private final BooksDAO booksDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleDAO peopleDAO, BooksDAO booksDAO, PersonValidator personValidator) {
        this.peopleDAO = peopleDAO;
        this.booksDAO = booksDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String getAllPeopleList(Model model){
        model.addAttribute("people", peopleDAO.getAll());
        return "people/allPeople";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id")int id, Model model){
        model.addAttribute("person", peopleDAO.findById(id));
        model.addAttribute("key", id);
        model.addAttribute("books", booksDAO.findByPerson(id));
        return "people/Person";
    }

    @GetMapping("/addNew")
    public String showAddingPerson(@ModelAttribute("person")Person person){
                return "people/addNew";
    }
    @PostMapping("/new")
    public String addPerson(@ModelAttribute("person")@Valid Person person,
                            BindingResult bindingResult){
    personValidator.validate(person,bindingResult);
         if(!bindingResult.hasErrors()) {
             peopleDAO.addPerson(person);
             return "redirect:/people";
         }
         return "people/addNew";
    }

    @DeleteMapping("/{id}/delete")
    public String deletePerson(@PathVariable("id") int id) {
        System.out.println(id);
        peopleDAO.deletePerson(id);
        //booksDAO.releaseBook();
        return "redirect:/people/";
    }

    @GetMapping("/{id}/edit")
    public String showEditPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("personToEdit", peopleDAO.findById(id));
        return "people/editPerson";
    }
    @PatchMapping("/{id}")
    public String editPerson(@PathVariable("id")int id, @ModelAttribute("personToEdit")
                            @Valid Person person, BindingResult bindingResult){

        if(!bindingResult.hasErrors()) {
            peopleDAO.editPerson(id,person);
            return "redirect:/people";
        }
        return "people/editPerson";
    }
}
