package nl.spelberg.brandweer.model;

import java.util.List;

public interface ExportService {

    String personsAllAsCsv();

    String personsAsCsv(List<Person> personsForExport);

    void exportPersonsAsCsv(String personsAsCsv);

    void exportAllPhotos();

    int exportAllAndCleanUp();

    int exportAndCleanUp(List<Person> persons);

    void exportAllWithEmail();

    List<Person> exportAll(List<Person> personsForExport);

    List<Person> exportPhotos(List<Person> personsForExport);
}
