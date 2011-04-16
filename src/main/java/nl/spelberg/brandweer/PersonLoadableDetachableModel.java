package nl.spelberg.brandweer;

import org.apache.wicket.model.LoadableDetachableModel;
import org.springframework.util.Assert;

public class PersonLoadableDetachableModel extends LoadableDetachableModel<Person> {
    private final Long personId;
    private final PersonService personService;

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
