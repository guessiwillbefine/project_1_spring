package vadim.andreich.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import vadim.andreich.service.BookService;
import vadim.andreich.service.PeopleService;
import vadim.andreich.util.BookValidator;
import org.springframework.ui.Model;
import vadim.andreich.models.Person;
import vadim.andreich.DAO.PeopleDAO;
import vadim.andreich.DAO.BooksDAO;
import vadim.andreich.models.Book;
import vadim.andreich.util.Temp;

import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookValidator bookValidator;
    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BookValidator bookValidator, BookService bookService, PeopleService peopleService) {
        this.bookValidator = bookValidator;
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String getAllPeopleList(Model model) {
        model.addAttribute("bookss", bookService.getAll());
        return "books/allBooks";
    }

    @GetMapping("/{id}")
    public String showBook(@ModelAttribute("id") int id,
                           @ModelAttribute("person") Person person,
                           Model model) {
        model.addAttribute("shownbook", bookService.findById(id).get());
        model.addAttribute("key", id);
        if (bookService.findOwner(id) != null) {
            System.out.println("popal v ne null");
            //System.out.println(booksDAO.findOwner(id));
            model.addAttribute("owner", bookService.findOwner(id));
        } else {
            System.out.println("popal v null");
            System.out.println("mimo");
            model.addAttribute("people", peopleService.getAll());
        }
        return "books/book";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteBook(@ModelAttribute("id") int id) {
        System.out.println(id);
        bookService.deleteById(id);
        return "redirect:/people/";
    }

    @GetMapping("/{id}/edit")
    public String showEditBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("bookToEdit", bookService.findById(id).get());
        return "books/editBook";
    }

    @GetMapping("/search")
    public String search(Model model) {
        model.addAttribute("word", new Temp());
        return "books/search";
    }

    @GetMapping("/request")
    public String request(Model model, @RequestParam(value = "param")String request) {
        System.out.println(request);
        model.addAttribute("word", new Temp(request));
        model.addAttribute("response",bookService.getBooksByLetter(request));
        return "books/search";
    }

    //    @GetMapping("books/search")
//    public String responseOfSearch(Model model, @RequestParam("request")String request){
//
//        //по реквесту получить в сервисе книги и положить в модель
//        model.addAttribute("response");
//        return "books/search";
//    }
    @PatchMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, @ModelAttribute("bookToEdit") @Valid Book updBook, BindingResult bindingResult) {
        bookValidator.validate(updBook, bindingResult);
        if (!bindingResult.hasErrors()) {
            bookService.editBook(id, updBook);
            return "redirect:/books";
        }
        return "books/editBook";
    }

    @GetMapping("/addNew")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/addNew";
    }

    @PostMapping("/new")
    public String addBook(@ModelAttribute("book") @Valid Book book,
                          BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (!bindingResult.hasErrors()) {
            bookService.addBook(book);
            return "redirect:/books";
        }
        return "/books/addNew";
    }

    @PostMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id) {
        bookService.releaseBook(id);
        return "redirect:/books";
    }

    @PostMapping("/{id}/set")
    public String releaseBook(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookService.giveBook(id, person.getId());
        return "redirect:/books";
    }

}
