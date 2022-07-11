package vadim.andreich.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.jetbrains.annotations.NotNull;
import vadim.andreich.DAO.PeopleDAO;
import vadim.andreich.models.Person;
import java.util.Objects;

@Component
public class PersonValidator implements Validator {
    private final PeopleDAO peopleDAO;

    @Autowired
    public PersonValidator(PeopleDAO peopleDAO) {
        this.peopleDAO = peopleDAO;
    }

    @Override
    public boolean supports(@NotNull Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(@NotNull Object o, @NotNull Errors errors) {
        Person person = (Person) o;
        if (peopleDAO.findByName(person.getName()).isPresent()) {
            Person foundedPerson = peopleDAO.findByName(person.getName()).get();
            if (Objects.equals(person.getName(), foundedPerson.getName())) {
                errors.rejectValue("name","","already in DB");
            }
        }
    }
}
