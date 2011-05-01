package nl.spelberg.brandweer;

import nl.spelberg.brandweer.model.Person;
import nl.spelberg.brandweer.model.PersonService;
import nl.spelberg.util.wicket.DefaultFocusBehavior;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.springframework.util.Assert;

public class PersonPanel extends Panel {

    private static final Log log = LogFactory.getLog(PersonPanel.class);

    public PersonPanel(String id, IModel<Person> personModel, Page returnPage) {
        super(id, personModel);
        add(new PersonForm("personForm", personModel, returnPage));
    }

    private static class PersonForm extends Form<Person> {

        @SpringBean
        private PersonService personService;

        private final Page returnPage;

        public PersonForm(String id, final IModel<Person> personModel, Page returnPage) {
            super(id, personModel);
            Assert.notNull(returnPage, "returnPage is null");
            this.returnPage = returnPage;

            FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");

            RequiredTextField<String> nameTextField = new RequiredTextField<String>("Naam", new PropertyModel<String>(
                    personModel, "name"));
            nameTextField.add(new DefaultFocusBehavior());
            FormComponent<String> emailTextField = new RequiredTextField<String>("Email", new PropertyModel<String>(
                    personModel, "email"));
            emailTextField.add(EmailAddressValidator.getInstance());
            Button submitButton = new Button("submit") {
                @Override
                public void onSubmit() {
                    Person person = personModel.getObject();
                    log.info("Person: " + person);
                    personService.updatePerson(person);
                    setResponsePage(PersonForm.this.returnPage);
                }
            };

            add(feedbackPanel);
            add(nameTextField);
            add(emailTextField);
            add(submitButton);
        }
    }

}
