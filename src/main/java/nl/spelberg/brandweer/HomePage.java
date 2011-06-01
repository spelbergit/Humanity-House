package nl.spelberg.brandweer;

import nl.spelberg.brandweer.model.BrandweerConfig;
import nl.spelberg.brandweer.model.ConfigService;
import nl.spelberg.brandweer.model.Person;
import nl.spelberg.brandweer.model.PersonService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;

public class HomePage extends WebPage {

    private static final Log log = LogFactory.getLog(HomePage.class);

    @SpringBean(name = "configService")
    private ConfigService configService;

    @SpringBean(name = "personService")
    private PersonService personService;

    public HomePage() {

        BrandweerConfig brandweerConfig = configService.getConfig();

        add(new AbstractAjaxTimerBehavior(Duration.seconds(brandweerConfig.getTimingHome())) {
            @Override
            protected void onTimer(AjaxRequestTarget target) {
                checkForNewPhoto();
            }
        });

        checkForNewPhoto();
    }

    private void checkForNewPhoto() {
        try {
            // check for new photo
            if (personService.hasNewPerson()) {
                Person person = personService.getMostRecentPerson();
                setResponsePage(new EnterDetailsPage(person));
                setRedirect(true);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            log.debug(e.getMessage(), e);
        }
    }

}
