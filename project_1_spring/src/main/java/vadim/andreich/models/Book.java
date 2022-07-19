package vadim.andreich.models;


import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBook")
    private int id;

    @ManyToOne
    @JoinColumn(name = "Person_idPerson", referencedColumnName = "id")
    private Person owner;

    @Pattern(regexp = ".+", message = "not null")
    @Column(name = "BookName")
    private String BookName;
    @Pattern(regexp = "\\w+\\.\\w\\. \\w+", message = "format : N.M. Surname")
    @Column(name = "author")
    private String author;
    @Column(name = "year")
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

    public String getAuthor() {
        return author;
    }

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
        return "[" + BookName + ",  " + author + ", " + year + "]";
    }
}
