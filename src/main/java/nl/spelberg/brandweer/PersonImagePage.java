package nl.spelberg.brandweer;

import nl.spelberg.brandweer.model.Person;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

/**
 * TODO: write documentation.
 */
public class PersonImagePage extends AbstractPage {

    public PersonImagePage(final Page page, IModel<Person> personModel) {
        super("Foto");
        add(
                new Link("back") {

                    @Override
                    public void onClick() {
                        setResponsePage(page);
                    }


                }.add(new Image("image", new PersonDynamicImageResource(personModel))));
    }
}
