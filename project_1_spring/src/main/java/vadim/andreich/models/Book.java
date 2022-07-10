package vadim.andreich.models;

import javax.validation.constraints.NotEmpty;
public class Book {
    private int id;

    @NotEmpty
    private String BookName;
    @NotEmpty
    private String author;
    private int year;
    public int getId() {
        return id;
    }
    public void setIdBook(int idBook) {
        this.id = idBook;
    }
    public String getBookName() {
        return BookName;
    }
    public void setBookName(String bookName) {
        BookName = bookName;
    }
    public String getAuthor() {return author;}
    public void setAuthor(String author) {
        this.author = author;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    @Override
    public String toString() {
        return "["+BookName + ",  " + author + ", " + year+"]";
    }
}