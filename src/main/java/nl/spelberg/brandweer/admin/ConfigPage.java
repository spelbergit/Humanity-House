package nl.spelberg.brandweer.admin;

import nl.spelberg.brandweer.model.ConfigService;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class ConfigPage extends WebPage {

    @SpringBean
    private ConfigService configService;

    public ConfigPage() {

        add(new FeedbackPanel("feedback"));
        add(new BookmarkablePageLink<Void>("adminLink", AdminPage.class));

    }

}
