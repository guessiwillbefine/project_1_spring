package vadim.andreich.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import vadim.andreich.DAO.BooksDAO;
import vadim.andreich.models.Book;
import vadim.andreich.service.BookService;
import vadim.andreich.service.PeopleService;


@Component
public class BookValidator implements Validator {
private final BookService service;
    @Autowired
    public BookValidator(BookService service) {
        this.service = service;
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;
        if (service.findByName(book.getBookName())!=null)
            errors.rejectValue("bookName", "", "this book is already in db");
    }
}
