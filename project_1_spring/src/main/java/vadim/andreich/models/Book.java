package vadim.andreich.models;


import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
@Table(name = "book")
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBook")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Person_idPerson", referencedColumnName = "id")
    private Person owner;

    @Pattern(regexp = ".+", message = "not null")
    @Column(name = "BookName")
    private String BookName;
    @Pattern(regexp = "\\w\\. \\w+", message = "format : N.M. Surname")
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

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "[" + BookName + ",  " + author + ", " + year + "]";
    }
}
