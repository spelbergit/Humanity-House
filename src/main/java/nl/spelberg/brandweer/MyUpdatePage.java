package nl.spelberg.brandweer;

import org.apache.wicket.markup.html.WebPage;
import org.wicketrad.jpa.propertyeditor.BeanForm;
import org.wicketrad.jpa.propertyeditor.DefaultUpdateBeanForm;

public class MyUpdatePage extends WebPage {

    public MyUpdatePage(MyBean bean) {
        BeanForm<MyBean> form = new DefaultUpdateBeanForm<MyBean>("form", bean) {
            protected void afterSubmit() {
                this.setResponsePage(MyListPage.class);
            }
        };
        add(form);
    }
}
