package nl.spelberg.brandweer.admin;

import nl.spelberg.brandweer.AbstractPage;
import nl.spelberg.util.wicket.DynamicDownloadLink;
import nl.spelberg.util.wicket.StringDynamicDownloadResource;
import org.apache.wicket.markup.html.basic.Label;
import static nl.spelberg.util.wicket.ContentType.TEXT_CSV;

public class AdminPage extends AbstractPage {


    public AdminPage() {
        super("Admin Page");

        StringDynamicDownloadResource dynamicDownloadResource = new StringDynamicDownloadResource("emailadressen.csv",
                TEXT_CSV) {
            @Override
            public String loadContent() {
                return "Test content";
            }
        };
        add(new DynamicDownloadLink("csvLink", dynamicDownloadResource).add(new Label("linkText",
                dynamicDownloadResource.getFileName())));
    }

}
