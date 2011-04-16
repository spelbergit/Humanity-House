package nl.spelberg.brandweer;

import nl.spelberg.brandweer.model.Person;
import nl.spelberg.brandweer.model.PersonService;
import nl.spelberg.brandweer.model.PhotoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.extensions.markup.html.image.resource.ThumbnailImageResource;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class EnterDetailsPage extends AbstractPage {

    private static final Log log = LogFactory.getLog(EnterDetailsPage.class);

    public static final String PERSON_ID = "personId";

    @SpringBean(name = "personService")
    private PersonService personService;

    @SpringBean(name = "photoService")
    private PhotoService photoService;

    public EnterDetailsPage() {
        super("Vul je gegevens in");

        Person person = personService.getMostRecentPerson();

        log.info("Most recent person: " + person);

        final LoadableDetachableModel<Person> personModel = new PersonLoadableDetachableModel(person, personService);
        add(new PersonPanel("personPanel", personModel));
        //noinspection WicketForgeJavaIdInspection
        add(
                new Link("superSizeMe") {
                    @Override
                    public void onClick() {
                        setResponsePage(new PersonImagePage(EnterDetailsPage.this, personModel));
                    }
                }.add(
                        new Image(
                                "image", new ThumbnailImageResource(
                                        new PersonDynamicImageResource(personModel, photoService), 400))));
    }


}
