package nl.spelberg.brandweer.admin;

import java.io.File;
import nl.spelberg.brandweer.AbstractPage;
import nl.spelberg.brandweer.model.BrandweerConfig;
import nl.spelberg.brandweer.model.ExportService;
import nl.spelberg.brandweer.model.PersonService;
import nl.spelberg.util.wicket.DynamicDownloadLink;
import nl.spelberg.util.wicket.StringDynamicDownloadResource;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.util.StringUtils;
import static nl.spelberg.util.wicket.ContentType.TEXT_CSV;

public class AdminPage extends AbstractPage {

    @SpringBean
    private ExportService exportService;

    @SpringBean
    private PersonService personService;

    @SpringBean
    private BrandweerConfig brandweerConfig;

    public AdminPage() {
        super("Admin Page");

        // Download CSV link
        StringDynamicDownloadResource dynamicDownloadResource = new StringDynamicDownloadResource("emailadressen.csv",
                TEXT_CSV) {
            @Override
            public String loadContent() {
                return exportService.exportAsCsv();
            }
        };
        //noinspection WicketForgeJavaIdInspection
        add(new DynamicDownloadLink("exportCsvLink", dynamicDownloadResource).add(new Label("linkText",
                dynamicDownloadResource.getFileName())));

        // Export photo's link
        add(new Link("exportPhotosLink") {
            @Override
            public void onClick() {
                exportService.exportPhotos();
                setResponsePage(AdminPage.class);
            }
        });
        add(new Label("exportDir", StringUtils.replace(brandweerConfig.getExportDir(), "/", File.separator)));

        // Number of registered persons link
        add(new Label("aantalPersonen", new LoadableDetachableModel<String>() {
            @Override
            protected String load() {
                return String.valueOf(personService.countPersons());
            }
        }));
    }

}
