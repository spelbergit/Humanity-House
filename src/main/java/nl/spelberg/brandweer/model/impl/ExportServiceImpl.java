package nl.spelberg.brandweer.model.impl;

import com.mindprod.csv.CSVWriter;
import java.io.StringWriter;
import java.util.List;
import nl.spelberg.brandweer.dao.PersonDAO;
import nl.spelberg.brandweer.model.BrandweerConfig;
import nl.spelberg.brandweer.model.ExportService;
import nl.spelberg.brandweer.model.FileOperations;
import nl.spelberg.brandweer.model.Person;
import nl.spelberg.util.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("exportService")
@Transactional
public class ExportServiceImpl implements ExportService {

    private static final Log log = LogFactory.getLog(ExportServiceImpl.class);

    @Autowired
    BrandweerConfig brandweerConfig;

    @Autowired
    PersonDAO personDAO;

    @Autowired
    FileOperations fileOperations;

    @Override
    public String exportAsCsv() {
        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(stringWriter);

        // header
        csvWriter.put("ID");
        csvWriter.put("Naam");
        csvWriter.put("Email");
        csvWriter.put("Foto");
        csvWriter.nl();

        // data
        List<Person> persons = personDAO.all();
        for (Person person : persons) {
            csvWriter.put(Utils.emptyWhenNullString(person.id()));
            csvWriter.put(Utils.emptyWhenNull(person.name()));
            csvWriter.put(Utils.emptyWhenNull(person.email()));
            csvWriter.put(person.photo().asHumanityHouseName(brandweerConfig.getImagePrefix()));
            csvWriter.nl();
        }


        return stringWriter.toString();
    }

    @Override
    public void exportPhotos() {
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
    public void cleanUp() {
        throw new UnsupportedOperationException("Not implemented.");
    }
}
