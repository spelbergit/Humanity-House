package nl.spelberg.brandweer;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

public abstract class AbstractPage extends WebPage {

    private final Model<String> titleModel;

    public AbstractPage(String title) {
        this(new Model<String>(title));
    }

    public AbstractPage(Model<String> titleModel) {
        this.titleModel = titleModel;

        add(new Label("title", this.titleModel));
    }

    public final Model<String> getTitleModel() {
        return titleModel;
    }

    public final String getTitle() {
        return titleModel.getObject();
    }

    public final void setTitle(String title) {
        this.titleModel.setObject(title);
    }
}
