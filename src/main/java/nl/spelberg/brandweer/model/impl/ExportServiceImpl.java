package nl.spelberg.brandweer.model.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private static final SimpleDateFormat DATEFORMAT_YYYYMMDD_HHMMSS = new SimpleDateFormat("yyyyMMdd-hhmmss");

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
                String photo = person.photoAsHumanityHouseFileName(brandweerConfig.getImagePrefix(),
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
    public List<Person> exportPhotos(List<Person> personsForExport) {
        BrandweerConfig brandweerConfig = configService.getConfig();

        fileOperations.createDirectoryRecursive(brandweerConfig.getExportDir());

        ArrayList<Person> personsWithOriginalPhoto = new ArrayList<Person>();
        for (Person person : personsForExport) {
            String fromPath = person.photo().path();
            String toPath = exportPhotoPath(person);
            try {
                fileOperations.copyFile(fromPath, toPath, FileOperations.CopyOption.SKIP_EXISTING);
                personsWithOriginalPhoto.add(person);
            } catch (NotFoundFileOperationException e) {
                log.debug("Person '" + person + "' heeft geen originele foto meer.");
            } catch (Exception e) {
                log.warn("Foto: " + fromPath + " kan niet worden gekopieerd naar: '" + toPath + "': " + e.getMessage());
            }
        }
        log.info("Exporteer foto's: " + personsWithOriginalPhoto.size() + " foto's geexporteerd naar " + brandweerConfig.getExportDir());

        return personsWithOriginalPhoto;
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
    public List<Person> exportAll(List<Person> personsForExport) {
        List<Person> exportedPersons = exportPhotos(personsForExport);
        exportPersonsAsCsv(personsAsCsv(exportedPersons));

        return exportedPersons;
    }

    @Override
    public void exportAllWithEmail() {
        List<Person> personsForExport = personDAO.allWithEmail();
        List<Person> exportedPersons = exportAll(personsForExport);

        List<Person> removedPersons = new ArrayList<Person>();
        for (Person personForExport : personsForExport) {
            if (!exportedPersons.contains(personForExport)) {
                removedPersons.add(personForExport);
            }
        }
        archivePersons(removedPersons);
    }

    @Override
    public void exportPersonsAsCsv(String personsAsCsv) {
        BrandweerConfig brandweerConfig = configService.getConfig();
        exportPersonsAsCsv(personsAsCsv, brandweerConfig.getExportDir());
    }

    private void exportPersonsAsCsv(String personsAsCsv, String exportDir) {
        String fileName = exportDir + "/emailadressen.csv";
        fileOperations.write(fileName, personsAsCsv);
        log.info("exported photos to csv: " + Utils.nativePath(fileName));
    }

    public void archivePersons(List<Person> persons) {
        if (!persons.isEmpty()) {
            BrandweerConfig brandweerConfig = configService.getConfig();

            String archiveDir = brandweerConfig.getExportDir() + "/archief-" + DATEFORMAT_YYYYMMDD_HHMMSS.format(new Date());
            fileOperations.createDirectoryRecursive(archiveDir);

            String personsAsCsv = personsAsCsv(persons);
            exportPersonsAsCsv(personsAsCsv, archiveDir);

            // remove traces...
            for (Person person : persons) {
                String exportedPhotoPath = exportPhotoPath(person);
                if (fileOperations.fileExists(exportedPhotoPath)) {
                    fileOperations.moveFileToDir(exportedPhotoPath, archiveDir);
                }
                // delete persons from database
                personDAO.delete(person);
            }
        }
    }

    private String exportPhotoPath(Person person) {
        BrandweerConfig brandweerConfig = configService.getConfig();
        return brandweerConfig.getExportDir() + "/" + person.photoAsHumanityHouseFileName(
                brandweerConfig.getImagePrefix(), brandweerConfig.getImagePrefixReplacement());
    }


}
