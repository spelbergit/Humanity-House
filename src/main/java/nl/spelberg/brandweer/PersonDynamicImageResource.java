package nl.spelberg.brandweer;

import org.apache.wicket.markup.html.image.resource.DynamicImageResource;
import org.apache.wicket.model.IModel;

public class PersonDynamicImageResource extends DynamicImageResource {
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
