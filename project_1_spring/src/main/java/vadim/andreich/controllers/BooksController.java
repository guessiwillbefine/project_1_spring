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
import vadim.andreich.models.Book;

import javax.validation.Valid;

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
    public String getAllPeopleList(Model model,
                                   @RequestParam(value = "length", required = false)Integer length,
                                   @RequestParam(value = "sort", required = false)String sort) {

        if (length == null && sort == null) {
            model.addAttribute("bookss", bookService.getAll());
            model.addAttribute("flag", true);
            System.out.println("нет сортировки + нет длинны");
        } else if (length != null && sort != null) {
            model.addAttribute("bookss", bookService.getAll(0, length, sort));
            model.addAttribute("flag", true);
            System.out.println("есть сортировка + есть длинна");
        }
        else if (length != null) {
            model.addAttribute("bookss", bookService.getAll(0, length));
            model.addAttribute("flag", true);
            System.out.println("нет сортировки + есть длинна");
        } else {
            model.addAttribute("bookss", bookService.getAll(sort));
            model.addAttribute("flag", true);
            System.out.println("есть сортировка + нет длинны");
        }
        model.addAttribute("sort", sort);
        model.addAttribute("length", length);
        return "books/allBooks";
    }

    @GetMapping("/next")
    public String getNextPage(Model model, @RequestParam("length") Integer length,
                              @RequestParam("sort")String sort){
        model.addAttribute("bookss", bookService.getAll(1, length, sort));
        model.addAttribute("length", length);
        model.addAttribute("sort", sort);
        model.addAttribute("flag", true);
        return "/books/allBooks";
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

    @GetMapping("/{id}/edit")
    public String showEditBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("bookToEdit", bookService.findById(id).get());
        return "books/editBook";
    }

    @GetMapping("/search")
    public String search(Model model) {
        model.addAttribute("word", "");
        return "books/search";
    }

    @GetMapping("/request")
    public String request(Model model, @RequestParam(value = "param")String request) {
        System.out.println(request);
        model.addAttribute("word", request);
        model.addAttribute("response", bookService.getBooksByLetter(request));
        return "books/search";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteBook(@ModelAttribute("id") int id) {
        System.out.println(id);
        bookService.deleteById(id);
        return "redirect:/people/";
    }

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
