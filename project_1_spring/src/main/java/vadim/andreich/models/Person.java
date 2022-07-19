package vadim.andreich.models;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Pattern(regexp = "\\w+ \\w+ \\w+", message = "pls enter correct name")
    @Column(name = "name")
    private String name;
    @Pattern(regexp = "\\d{1,2}\\.\\d{1,2}\\.\\d{4}", message = "date must be in dd.mm.yy format")
    @Column(name = "birthday")
    private String birthday;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;


    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setName(String fullName) {this.name = fullName;}
    public void setBirthday(String birth) {this.birthday = birth;}
    public void setId(int idPerson) {this.id = idPerson;}
    public String getName() { return name; }
    public String getBirthday() { return birthday; }
    public int getId() { return id; }
    @Override
    public String toString() {
        return name + ", " + birthday;
    }
}
