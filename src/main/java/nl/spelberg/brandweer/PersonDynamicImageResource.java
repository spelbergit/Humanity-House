package nl.spelberg.brandweer;

import nl.spelberg.brandweer.model.Person;
import nl.spelberg.brandweer.model.Photo;
import nl.spelberg.brandweer.model.PhotoService;
import org.apache.wicket.markup.html.image.resource.DynamicImageResource;
import org.apache.wicket.model.IModel;
import org.springframework.util.Assert;

public class PersonDynamicImageResource extends DynamicImageResource {

    private final PhotoService photoService;

    private final IModel<Person> personModel;

    public PersonDynamicImageResource(IModel<Person> personModel, PhotoService photoService) {
        Assert.notNull(personModel, "personModel is null");
        Assert.notNull(photoService, "photoService is null");
        this.personModel = personModel;
        this.photoService = photoService;
    }

    @Override
    protected byte[] getImageData() {
        Person person = personModel.getObject();
        Photo photo = person.foto();
        return photoService.readFotoData(photo);
    }

    public Photo getPhoto() {
        return personModel.getObject().foto();
    }
}
