package nl.spelberg.brandweer.model;

import java.util.List;

public interface ExportService {

    String personsAllAsCsv();

    String personsAsCsv(List<Person> personsForExport);

    void exportAllPhotos();

    void exportPhotos(List<Person> personsForExport);

    int exportAllAndCleanUp();

    int exportAndCleanUp(List<Person> persons);
}
