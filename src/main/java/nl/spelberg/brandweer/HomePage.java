package nl.spelberg.brandweer;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class HomePage extends AbstractPage {

    public HomePage() {
        super("HomePage");
        add(
                new BookmarkablePageLink<EnterDetailsPage>(
                        "enterDetailsLink", EnterDetailsPage.class, new PageParameters(
                                EnterDetailsPage.PERSON_ID + "=12345")));
    }
}
