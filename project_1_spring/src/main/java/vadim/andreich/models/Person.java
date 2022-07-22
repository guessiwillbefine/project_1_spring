package vadim.andreich.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person {
    @Pattern(regexp = "\\w+ \\w+ \\w+", message = "pls enter correct name")
    @Column(name = "name")
    private String name;
    @Pattern(regexp = "\\d{1,2}\\.\\d{1,2}\\.\\d{4}", message = "date must be in dd.mm.yy format")
    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date birthday;
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
    public void setBirthday(Date birth) {this.birthday = birth;}
    public void setId(int idPerson) {this.id = idPerson;}
    public String getName() { return name; }
    public Date getBirthday() { return birthday; }
    public int getId() { return id; }
    @Override
    public String toString() {
        return name + ", " + birthday;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Objects.equals(name, person.name) && Objects.equals(birthday, person.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthday, id);
    }
}
