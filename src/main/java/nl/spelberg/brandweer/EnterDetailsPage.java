package nl.spelberg.brandweer;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import nl.spelberg.util.Utils;
import org.apache.wicket.Application;
import org.apache.wicket.PageParameters;
import org.apache.wicket.extensions.markup.html.image.resource.ThumbnailImageResource;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.protocol.http.WebApplication;

public class EnterDetailsPage extends AbstractPage {

    private static final Logger log = Logger.getLogger(EnterDetailsPage.class.getName());

    public static final String PERSON_ID = "personId";

    public EnterDetailsPage(PageParameters pageParameters) {
        super("Vul je gegevens in");

        final Long personId = pageParameters.getLong(PERSON_ID);
        if (personId == null) {
            log.info(
                    "No '" + PERSON_ID + "' parameter in " + this.getClass().getSimpleName() + ", redirecting to " +
                            HomePage.class.getName() + "...");
            setResponsePage(HomePage.class);
            return;
        }

        final LoadableDetachableModel<Person> personModel = new LoadableDetachableModel<Person>() {
            @Override
            protected Person load() {
                try {
                    ServletContext servletContext = ((WebApplication) Application.get()).getServletContext();
                    InputStream inputStream = servletContext.getResourceAsStream(
                            "/images/Amsterdam-overstroming-03.jpg");
                    byte[] fotoData = Utils.getBytes(inputStream);
                    return new Person("", "rufus@branie.nl", "IMG001", "jpg", fotoData);
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        };
        add(new PersonPanel("personPanel", personModel));
        add(
                new Link("superSizeMe") {
                    @Override
                    public void onClick() {
                        setResponsePage(new PersonImagePage("", EnterDetailsPage.this, personModel));
                    }
                }.add(
                        new Image(
                                "image", new ThumbnailImageResource(
                                        new PersonDynamicImageResource(personModel), 400))));
    }


}
