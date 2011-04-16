package nl.spelberg.brandweer;

import java.util.logging.Logger;
import org.apache.wicket.PageParameters;
import org.apache.wicket.extensions.markup.html.image.resource.ThumbnailImageResource;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class EnterDetailsPage extends AbstractPage {

    private static final Logger log = Logger.getLogger(EnterDetailsPage.class.getName());

    public static final String PERSON_ID = "personId";

    @SpringBean(name = "personService")
    private PersonService personService;

    public EnterDetailsPage(PageParameters pageParameters) {
        super("Vul je gegevens in");

        Long personId = pageParameters.getLong(PERSON_ID);
        if (personId == null) {
            log.info(
                    "No '" + PERSON_ID + "' parameter in " + this.getClass().getSimpleName() + ", redirecting to " +
                            HomePage.class.getName() + "...");
            setResponsePage(HomePage.class);
            return;
        }

        final LoadableDetachableModel<Person> personModel = new PersonLoadableDetachableModel(
                personId, personService);
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
                                        new PersonDynamicImageResource(personModel), 400))));
    }


}
