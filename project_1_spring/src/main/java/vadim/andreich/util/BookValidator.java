package vadim.andreich.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import vadim.andreich.DAO.BooksDAO;
import vadim.andreich.models.Book;


@Component
public class BookValidator implements Validator {
    private final BooksDAO booksDAO;

    @Autowired
    public BookValidator(BooksDAO booksDAO) {
        this.booksDAO = booksDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
//        Book book = (Book) o;
//        if (booksDAO.findByName(book.getBookName()).isPresent())
//            errors.rejectValue("bookName", "", "this book is already in db");
//    }
    }
}
