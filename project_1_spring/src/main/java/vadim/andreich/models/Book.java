package vadim.andreich.models;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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
    private String bookName;
    @Pattern(regexp = "\\w\\. \\w+", message = "format : N.M. Surname")
    @Column(name = "author")
    private String author;
    @Column(name = "year")
    private int year;
    @Column(name = "giving_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date givingTime;
    @Transient
    private boolean IsOverdue;
    public void setId(int id) {this.id = id;}
    public Date getGivingTime() {return givingTime;}
    public void setGivingTime(Date givingTime) {this.givingTime = givingTime;}
    public int getId() {
        return id;
    }
    public void setIdBook(int idBook) {
        this.id = idBook;
    }
    public String getBookName() {
        return bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
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
    public boolean hasOwner(){return owner != null;}
    @Override
    public String toString() {
        return "[" + bookName + ",  " + author + ", " + year + "]";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && year == book.year && givingTime == book.givingTime &&
                Objects.equals(bookName, book.bookName) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookName, author);
    }
    public boolean isOverdue() {
        return IsOverdue;
    }
    public void setOverdue(boolean overdue) {
        IsOverdue = overdue;
    }
}
