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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("exportService")
@Transactional
public class ExportServiceImpl implements ExportService {

    private static final Log log = LogFactory.getLog(ExportServiceImpl.class);

    @Autowired
    private ConfigService configService;

    @Autowired
    private PersonDAO personDAO;

    @Autowired
    private FileOperations fileOperations;

    @Override
    public String personsAllAsCsv() {
        return personsAsCsv(personDAO.all());
    }

    @Override
    public String personsAsCsv(List<Person> personsForExport) {
        try {
            BrandweerConfig brandweerConfig = configService.getConfig();

            StringWriter stringWriter = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(stringWriter);

            // header
            csvWriter.addLine("ID", "Naam", "Email", "Foto");

            // data
            for (Person person : personsForExport) {
                String personId = Utils.emptyWhenNullString(person.id());
                String name = Utils.emptyWhenNull(person.name());
                String email = Utils.emptyWhenNull(person.email());
                String photo = person.photoAsHumanityHouseName(brandweerConfig.getImagePrefix(),
                        brandweerConfig.getImagePrefixReplacement());
                csvWriter.addLine(personId, name, email, photo);
            }
            return stringWriter.toString();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void exportAllPhotos() {
        exportPhotos(personDAO.all());
    }

    @Override
    public void exportPhotos(List<Person> personsForExport) {
        exportPhotos(personsForExport, false);
    }

    @Override
    public void exportPhotosOnlyNew(List<Person> personsForExport) {
        exportPhotos(personsForExport, true);
    }

    private void exportPhotos(List<Person> personsForExport, boolean skipExisting) {
        BrandweerConfig brandweerConfig = configService.getConfig();

        fileOperations.createDirectoryRecursive(brandweerConfig.getExportDir());
        int count = 0;
        for (Person person : personsForExport) {
            String fromPath = person.photo().path();
            String toPath = brandweerConfig.getExportDir() + "/" + person.photoAsHumanityHouseName(
                    brandweerConfig.getImagePrefix(), brandweerConfig.getImagePrefixReplacement());
            try {
                FileOperations.CopyOption[] copyOptions = skipExisting ? new FileOperations.CopyOption[]{FileOperations.CopyOption.SKIP_EXISTING} : new FileOperations.CopyOption[0];
                fileOperations.copyFile(fromPath, toPath, copyOptions);
                count++;
            } catch (Exception e) {
                log.warn("Foto: " + fromPath + " kan niet worden gekopieerd naar: '" + toPath + "': " + e.getMessage());
            }
        }
        log.info("Exporteer foto's: " + count + " foto's geexporteerd naar " + brandweerConfig.getExportDir());
    }

    @Override
    public int exportAllAndCleanUp() {
        return exportAndCleanUp(personDAO.all());
    }

    @Override
    public int exportAndCleanUp(List<Person> personsForExport) {

        exportAll(personsForExport);

        int count = personDAO.deleteAll();
        log.info("Cleanup: deleted " + count + " persons.");

        return count;
    }

    @Override
    public void exportAll(List<Person> personsForExport) {
        exportPhotos(personsForExport);
        exportPersonsAsCsv(personsAsCsv(personsForExport));
        log.info("Export: exported " + personsForExport.size() + " persons.");
    }

    @Override
    public void exportAllWithEmailOnlyNew() {
        List<Person> personsForExport = personDAO.allWithEmail();
        exportPhotosOnlyNew(personsForExport);
        exportPersonsAsCsv(personsAsCsv(personsForExport));
    }

    @Override
    public void exportPersonsAsCsv(String personsAsCsv) {
        BrandweerConfig brandweerConfig = configService.getConfig();
        String fileName = brandweerConfig.getExportDir() + "/emailadressen.csv";
        fileOperations.write(fileName, personsAsCsv);
        log.info("exported all photos to csv: " + Utils.nativePath(fileName));
    }
}
