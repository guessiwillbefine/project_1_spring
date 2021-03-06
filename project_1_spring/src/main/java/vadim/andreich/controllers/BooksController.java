package vadim.andreich.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import vadim.andreich.util.BookValidator;
import org.springframework.ui.Model;
import vadim.andreich.models.Person;
import vadim.andreich.DAO.PeopleDAO;
import vadim.andreich.DAO.BooksDAO;
import vadim.andreich.models.Book;
import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookValidator bookValidator;
    private final BooksDAO booksDAO;
    private final PeopleDAO peopleDAO;

    @Autowired
    public BooksController(BookValidator bookValidator, BooksDAO booksDAO, PeopleDAO peopleDAO) {
        this.bookValidator = bookValidator;
        this.booksDAO = booksDAO;
        this.peopleDAO = peopleDAO;
    }

    @GetMapping()
    public String getAllPeopleList(Model model) {
        model.addAttribute("bookss", booksDAO.getAll());
        return "books/allBooks";
    }

    @GetMapping("/{id}")
    public String showBook(@ModelAttribute("id") int id,
                           @ModelAttribute("person") Person person,
                           Model model) {
        System.out.println(id);
        model.addAttribute("shownbook", booksDAO.findbyid(id));
        model.addAttribute("key", id);
        if (booksDAO.findOwner(id).isPresent()) {
            System.out.println(booksDAO.findOwner(id).get());
            model.addAttribute("owner", booksDAO.findOwner(id).get());
        } else {
            model.addAttribute("people", peopleDAO.getAll());
        }
        return "books/book";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteBook(@ModelAttribute("id") int id) {
        System.out.println(id);
        booksDAO.deleteBookById(id);
        return "redirect:/people/";
    }

    @GetMapping("/{id}/edit")
    public String showEditBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("bookToEdit", booksDAO.findbyid(id));
        System.out.println(booksDAO.findbyid(id));
        return "books/editBook";
    }

    @PatchMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, @ModelAttribute("bookToEdit") @Valid Book updBook, BindingResult bindingResult) {
        bookValidator.validate(updBook, bindingResult);
        if (!bindingResult.hasErrors()) {
            booksDAO.editBook(id, updBook);
            return "redirect:/books";
        }
        return "books/editBook";
    }

    @GetMapping("/addNew")
    public String newBook(@ModelAttribute("book")Book book) {
        return "books/addNew";
    }

    @PostMapping("/new")
    public String addBook(@ModelAttribute("book")@Valid Book book,
                          BindingResult bindingResult){
        bookValidator.validate(book, bindingResult);
        if (!bindingResult.hasErrors()){
            booksDAO.addBook(book);
            return "redirect:/books";
        }
        return "/books/addNew";
    }

    @PostMapping("/{id}/release")
    public String releaseBook(@PathVariable("id")int id){
        booksDAO.releaseBook(id);
        return "redirect:/books";
    }
    @PostMapping("/{id}/set")
    public String releaseBook(@PathVariable("id")int id, @ModelAttribute("person")Person person){
        booksDAO.giveBook(id, person.getId());
        return "redirect:/books";
    }

}
