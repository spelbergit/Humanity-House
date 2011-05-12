package nl.spelberg.brandweer.admin;

import nl.spelberg.brandweer.AbstractPage;
import nl.spelberg.brandweer.model.ExportService;
import nl.spelberg.brandweer.model.PersonService;
import nl.spelberg.util.wicket.DynamicDownloadLink;
import nl.spelberg.util.wicket.StringDynamicDownloadResource;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import static nl.spelberg.util.wicket.ContentType.TEXT_CSV;

public class AdminPage extends AbstractPage {

    @SpringBean
    private ExportService exportService;

    @SpringBean
    private PersonService personService;

    public AdminPage() {
        super("Admin Page");

        StringDynamicDownloadResource dynamicDownloadResource = new StringDynamicDownloadResource("emailadressen.csv",
                TEXT_CSV) {
            @Override
            public String loadContent() {
                return exportService.exportAsCsv();
            }
        };
        //noinspection WicketForgeJavaIdInspection
        add(new DynamicDownloadLink("csvLink", dynamicDownloadResource).add(new Label("linkText",
                dynamicDownloadResource.getFileName())));

        add(new Label("aantalPersonen", new LoadableDetachableModel<String>() {
            @Override
            protected String load() {
                return String.valueOf(personService.countPersons());
            }
        }));
    }

}
