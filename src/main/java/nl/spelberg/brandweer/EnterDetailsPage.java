package nl.spelberg.brandweer;

import nl.spelberg.brandweer.model.BrandweerConfig;
import nl.spelberg.brandweer.model.Person;
import nl.spelberg.brandweer.model.PersonService;
import nl.spelberg.brandweer.model.PhotoCache;
import nl.spelberg.brandweer.model.PhotoService;
import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;
import org.springframework.util.Assert;

public class EnterDetailsPage extends AbstractPage {

    @SpringBean(name = "brandweerConfig")
    private BrandweerConfig brandweerConfig;

    @SpringBean(name = "personService")
    private PersonService personService;

    @SpringBean(name = "photoService")
    private PhotoService photoService;

    @SpringBean
    private PhotoCache photoCache;

    public EnterDetailsPage(Person person) {
        super("Vul je gegevens in");
        Assert.notNull(person, "person is null");

        final LoadableDetachableModel<Person> personModel = new PersonLoadableDetachableModel(person, personService);
        add(new PersonPanel("personPanel", personModel, new FinishPage(person)));

        add(new Image("image", new PersonThumbnailImageResource(new PersonDynamicImageResource(personModel,
                photoService), brandweerConfig.getMaxPhotoSize(), photoCache)));

        add(new AbstractAjaxTimerBehavior(Duration.seconds(brandweerConfig.getTimingEmail())) {
            @Override
            protected void onTimer(AjaxRequestTarget target) {
                //                setResponsePage(HomePage.class);
                //                setRedirect(true);
            }
        });

    }

}
