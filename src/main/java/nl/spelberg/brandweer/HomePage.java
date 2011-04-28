package nl.spelberg.brandweer;

import nl.spelberg.brandweer.model.Person;
import nl.spelberg.brandweer.model.PersonService;
import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;

public class HomePage extends AbstractPage {

    @SpringBean(name = "personService")
    private PersonService personService;

    public HomePage() {
        super("HomePage");

        add(new AbstractAjaxTimerBehavior(Duration.seconds(5)) {
            @Override
            protected void onTimer(AjaxRequestTarget target) {
                // check for new photo
                if (personService.hasNewPerson()) {
                    Person person = personService.getMostRecentPerson();
                    setResponsePage(new EnterDetailsPage(person));
                    setRedirect(true);
                }
            }
        });

    }

}
