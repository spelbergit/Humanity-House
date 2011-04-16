package nl.spelberg.brandweer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;

public class PersonPanel extends Panel {
    private static final Log log = LogFactory.getLog(PersonPanel.class);

    public PersonPanel(String id, IModel<Person> personModel) {
        super(id, personModel);
        add(new PersonForm("personForm", personModel));
    }

    private static class PersonForm extends Form<Person> {

        public PersonForm(String id, final IModel<Person> personModel) {
            super(id, personModel);

            FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");

            RequiredTextField<String> nameTextField = new RequiredTextField<String>(
                    "name", new PropertyModel<String>(
                            personModel, "name"));

            FormComponent<String> emailTextField = new RequiredTextField<String>(
                    "email", new PropertyModel<String>(
                            personModel, "email"));
            emailTextField.add(EmailAddressValidator.getInstance());

            Button submitButton = new Button("submit") {
                @Override
                public void onSubmit() {
                    log.info("Person: " + personModel.getObject());
                    setResponsePage(HomePage.class);
                }
            };

            add(feedbackPanel);
            add(nameTextField);
            add(emailTextField);
            add(submitButton);
        }
    }

}
