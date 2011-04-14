package nl.spelberg.brandweer;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import nl.spelberg.util.Utils;
import org.apache.wicket.Application;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.resource.DynamicImageResource;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
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

        LoadableDetachableModel<Person> personModel = new LoadableDetachableModel<Person>() {
            @Override
            protected Person load() {
                byte[] fotoData = new byte[0];
                try {
                    ServletContext servletContext = ((WebApplication) Application.get()).getServletContext();
                    InputStream inputStream = servletContext.getResourceAsStream(
                            "/images/Amsterdam-overstroming-03.jpg");
                    fotoData = Utils.getBytes(inputStream);
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
                return new Person("Rufus Branieschopper", "rufus@branie.nl", "IMG001", "jpg", fotoData);
            }
        };
        add(new PersonForm("personForm", personModel));
        add(new Image("image", new PersonDynamicImageResource(personModel)));
    }

    private static class PersonForm extends Form<Person> {

        public PersonForm(String id, final IModel<Person> personModel) {
            super(id, personModel);
            Person person = personModel.getObject();
            add(new TextField<String>("name", new PropertyModel<String>(personModel, "name")));
            add(new TextField<String>("email", new PropertyModel<String>(personModel, "email")));
            add(
                    new Button("submit") {
                        @Override
                        public void onSubmit() {
                            log.info("Person: " + personModel.getObject());
                            setResponsePage(HomePage.class);
                        }
                    });
        }
    }

    private static class PersonDynamicImageResource extends DynamicImageResource {
        private IModel<Person> personModel;

        public PersonDynamicImageResource(IModel<Person> personModel) {
            this.personModel = personModel;
        }

        @Override
        protected byte[] getImageData() {
            Person person = personModel.getObject();
            return person.getFotoData();
        }
    }
}
