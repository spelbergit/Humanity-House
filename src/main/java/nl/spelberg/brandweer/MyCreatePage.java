package nl.spelberg.brandweer;

import org.apache.wicket.markup.html.WebPage;
import org.wicketrad.jpa.propertyeditor.BeanForm;
import org.wicketrad.jpa.propertyeditor.DefaultCreateBeanForm;

public class MyCreatePage extends WebPage {

    public MyCreatePage() {
        BeanForm form = new DefaultCreateBeanForm("form", new MyBean()) {
            protected void afterSubmit() {
                this.setResponsePage(MyListPage.class);
            }
        };
        add(form);
    }
}
