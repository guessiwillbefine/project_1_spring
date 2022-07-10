package vadim.andreich.models;
import javax.validation.constraints.Pattern;

public class Person {
    @Pattern(regexp = "\\w+ \\w+ \\w+", message = "pls enter full name")
    private String name;
    @Pattern(regexp = "\\d{1,2}\\.\\d{1,2}\\.\\d{4}", message = "date must be in dd.mm.yy format")
    private String birthday;

    private int id;
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
