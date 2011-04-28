package nl.spelberg.brandweer;

import nl.spelberg.brandweer.model.Person;
import nl.spelberg.brandweer.model.PersonService;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class HomePage extends AbstractPage {

    @SpringBean(name = "personService")
    private PersonService personService;

    public HomePage() {
        super("HomePage");

        // check for new photo
        if (personService.hasNewPerson()) {
            Person person = personService.getMostRecentPerson();
            setResponsePage(new EnterDetailsPage(person));
            setRedirect(true);
        }
    }
}
