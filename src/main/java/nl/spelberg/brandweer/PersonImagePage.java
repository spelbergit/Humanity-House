package nl.spelberg.brandweer;

import nl.spelberg.brandweer.model.Person;
import nl.spelberg.brandweer.model.PhotoService;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class PersonImagePage extends AbstractPage {

    @SpringBean(name = "photoService")
    private PhotoService photoService;

    public PersonImagePage(final Page page, IModel<Person> personModel) {
        super("Foto");
        //noinspection WicketForgeJavaIdInspection
        add(new Link("back") {
            @Override
            public void onClick() {
                setResponsePage(page);
            }
        }.add(new Image("image", new PersonDynamicImageResource(personModel, photoService))));
    }

}
