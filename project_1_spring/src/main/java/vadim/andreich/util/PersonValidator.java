package vadim.andreich.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import vadim.andreich.DAO.PeopleDAO;
import vadim.andreich.models.Person;
import vadim.andreich.service.PeopleService;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(@NotNull Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(@NotNull Object o, @NotNull Errors errors) {
        Person person = (Person) o;
        if (peopleService.findByName(person.getName()) != null) {
            Person foundedPerson = peopleService.findByName(person.getName());
            if (Objects.equals(person.getName(), foundedPerson.getName())) {
                errors.rejectValue("name","","already in DB");
            }
        }
    }
}
