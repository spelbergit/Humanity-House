package nl.spelberg.brandweer.model.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import nl.spelberg.brandweer.dao.PersonDAO;
import nl.spelberg.brandweer.model.BrandweerConfig;
import nl.spelberg.brandweer.model.ConfigService;
import nl.spelberg.brandweer.model.ExportService;
import nl.spelberg.brandweer.model.FileOperations;
import nl.spelberg.brandweer.model.Person;
import nl.spelberg.csv.CSVWriter;
import nl.spelberg.util.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("exportService")
@Transactional
public class ExportServiceImpl implements ExportService {

    private static final Log log = LogFactory.getLog(ExportServiceImpl.class);

    @SpringBean(name = "configService")
    private ConfigService configService;

    @Autowired
    PersonDAO personDAO;

    @Autowired
    FileOperations fileOperations;

    @Override
    public String exportAsCsv() {
        try {
            StringWriter stringWriter = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(stringWriter);

            // header
            csvWriter.addLine("ID", "Naam", "Email", "Foto");

            // data
            List<Person> persons = personDAO.all();
            for (Person person : persons) {
                String personId = Utils.emptyWhenNullString(person.id());
                String name = Utils.emptyWhenNull(person.name());
                String email = Utils.emptyWhenNull(person.email());
                String photo = person.photo().asHumanityHouseName(configService.getConfig().getImagePrefix());
                csvWriter.addLine(personId, name, email, photo);
            }
            return stringWriter.toString();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void exportPhotos() {
        BrandweerConfig brandweerConfig = configService.getConfig();

        List<Person> persons = personDAO.all();
        int count = 0;
        for (Person person : persons) {
            String fromPath = person.photo().path();
            String toPath = brandweerConfig.getExportDir() + "/" + person.photo().asHumanityHouseName(
                    brandweerConfig.getImagePrefix());
            try {
                fileOperations.copyFile(fromPath, toPath);
                count++;
            } catch (Exception e) {
                log.warn("Foto: " + fromPath + " kan niet worden gekopieerd naar: '" + toPath + "': " + e.getMessage());
            }
        }
        log.info("Exporteer foto's: " + count + " foto's geexporteerd naar " + brandweerConfig.getExportDir());
    }

    @Override
    public int exportAndCleanUp() {
        BrandweerConfig brandweerConfig = configService.getConfig();

        exportPhotos();
        String csv = exportAsCsv();
        String fileName = brandweerConfig.getExportDir() + "/emailadressen.csv";
        fileOperations.write(fileName, csv);
        log.info("Cleanup: exported all photos to csv: " + Utils.nativePath(fileName));

        int count = personDAO.deleteAll();
        log.info("Cleanup: deleted " + count + " persons.");

        return count;
    }
}
