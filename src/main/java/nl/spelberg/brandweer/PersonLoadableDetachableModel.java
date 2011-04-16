package nl.spelberg.brandweer;

import nl.spelberg.brandweer.model.Person;
import nl.spelberg.brandweer.model.PersonService;
import org.apache.wicket.model.LoadableDetachableModel;
import org.springframework.util.Assert;

public class PersonLoadableDetachableModel extends LoadableDetachableModel<Person> {

    private final PersonService personService;

    private final Long personId;

    public PersonLoadableDetachableModel(Person person, PersonService personService) {
        this(person.id(), personService);
    }

    public PersonLoadableDetachableModel(Long personId, PersonService personService) {
        Assert.notNull(personId, "personId is null");
        Assert.notNull(personService, "personService is null");
        this.personId = personId;
        this.personService = personService;
    }

    @Override
    protected Person load() {
        return personService.findPerson(personId);
    }
}
