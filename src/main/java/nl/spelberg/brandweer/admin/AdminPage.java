package nl.spelberg.brandweer.admin;

import nl.spelberg.brandweer.AbstractPage;
import nl.spelberg.brandweer.model.ConfigService;
import nl.spelberg.brandweer.model.ExportService;
import nl.spelberg.brandweer.model.PersonService;
import nl.spelberg.util.wicket.DynamicDownloadLink;
import nl.spelberg.util.wicket.StringDynamicDownloadResource;
import nl.spelberg.util.wicket.popup.YesNoPanel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import static nl.spelberg.util.wicket.ContentType.TEXT_CSV;

public class AdminPage extends AbstractPage {

    private static final Log log = LogFactory.getLog(AdminPage.class);

    @SpringBean
    private ExportService exportService;

    @SpringBean
    private PersonService personService;

    @SpringBean
    private ConfigService configService;

    public AdminPage() {
        super("Admin Page");

        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);

        // Download CSV link
        StringDynamicDownloadResource dynamicDownloadResource = new StringDynamicDownloadResource("emailadressen.csv",
                TEXT_CSV) {
            @Override
            public String loadContent() {
                return exportService.personsAllAsCsv();
            }
        };
        //noinspection WicketForgeJavaIdInspection
        add(new DynamicDownloadLink("exportCsvLink", dynamicDownloadResource).add(new Label("linkText",
                dynamicDownloadResource.getFileName())));

        // Export photo's link
        add(new Link("exportPhotosLink") {
            @Override
            public void onClick() {
                exportService.exportAllPhotos();
                Session.get().info("Export van foto's staat in: " + configService.getConfig().getExportDirNative());
                setResponsePage(AdminPage.class);
            }
        });
        add(new Label("exportDir", configService.getConfig().getExportDirNative()));

        // Number of registered persons link
        add(new Label("aantalPersonen", new LoadableDetachableModel<String>() {
            @Override
            protected String load() {
                return String.valueOf(personService.countPersons());
            }
        }));

        final ModalWindow okCancelWindow = YesNoPanel.createConfirmModal("cleanUpConfirmation",
                "Alle emailadressen verwijderen", "Alle ingevulde emailadressen verwijderen? (de foto's blijven staan)",
                new YesNoPanel.ConfirmationCallback() {

                    @Override
                    public void onConfirm(AjaxRequestTarget target) {
                        try {
                            exportService.exportAllAndCleanUp();
                            Session.get().info("Export van foto's en emailadressen staat in: " +
                                    configService.getConfig().getExportDirNative());
                            setResponsePage(AdminPage.class);
                        } catch (Exception e) {
                            log.error(e.getMessage(), e);
                            Session.get().error("Er is een fout opgetreden bij het verwijderen: " + e.getMessage());
                        }
                        target.addComponent(feedbackPanel);
                    }

                    @Override
                    public void onCancel(AjaxRequestTarget target) {
                        Session.get().info("Verwijderen is afgebroken; er is niets verwijderd.");
                        setResponsePage(AdminPage.class);
                    }
                });
        AjaxFallbackLink<Void> cleanUpLink = new AjaxFallbackLink<Void>("cleanUpLink") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                okCancelWindow.show(target);
            }
        };

        add(cleanUpLink);
        add(okCancelWindow);

        add(feedbackPanel);

        //        Link<Void> configLink = new AjaxFallbackLink<Void>("configLink") {
        //            @Override
        //            public void onClick(AjaxRequestTarget target) {
        //                setResponsePage(ConfigPage.class);
        //            }
        //        };
        //        add(configLink);
    }

}
