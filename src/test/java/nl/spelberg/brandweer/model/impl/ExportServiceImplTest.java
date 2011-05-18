package nl.spelberg.brandweer.model.impl;

import java.util.Arrays;
import java.util.List;
import nl.spelberg.brandweer.dao.PersonDAO;
import nl.spelberg.brandweer.model.BrandweerConfig;
import nl.spelberg.brandweer.model.ExportService;
import nl.spelberg.brandweer.model.FileOperations;
import nl.spelberg.brandweer.model.Person;
import nl.spelberg.brandweer.model.Photo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExportServiceImplTest {

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    BrandweerConfig brandweerConfig;

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private PersonDAO personDAO;

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private FileOperations fileOperations;

    @InjectMocks
    private ExportService exportService = new ExportServiceImpl();

    @Test
    public void testExportAsCsv() {
        //setup
        when(brandweerConfig.getImagePrefix()).thenReturn("IMG");

        List<Person> persons = mockPersons();
        when(personDAO.all()).thenReturn(persons);

        // execute
        String csv = exportService.exportAsCsv();

        // verify
        String expectedCsv = createExpectedCsv();

        assertEquals(expectedCsv, csv);
    }

    @Test
    public void testRenamePhotos() {
        // setup
        when(brandweerConfig.getImagePrefix()).thenReturn("IMG");
        when(brandweerConfig.getExportDir()).thenReturn("C:/Documents and Settings/hh/Afbeeldingen/Export");

        List<Person> persons = mockPersons();
        when(personDAO.all()).thenReturn(persons);

        // execute
        exportService.exportPhotos();

        // verify
        verify(fileOperations).copyFile("C:/Documents and Settings/hh/Afbeeldingen/DenHaag/IMG00001.JPG",
                "C:/Documents and Settings/hh/Afbeeldingen/Export/HumanityHouse-DenHaag_00001.jpg");
        verify(fileOperations).copyFile("C:/Documents and Settings/hh/Afbeeldingen/DenHaag/IMG00002.JPG",
                "C:/Documents and Settings/hh/Afbeeldingen/Export/HumanityHouse-DenHaag_00002.jpg");
        verify(fileOperations).copyFile("IMG00003.JPG",
                "C:/Documents and Settings/hh/Afbeeldingen/Export/HumanityHouse-00003.jpg");
        verify(fileOperations).copyFile("IMG00004.JPG",
                "C:/Documents and Settings/hh/Afbeeldingen/Export/HumanityHouse-00004.jpg");
    }

    @Test
    public void testCleanUp() {
        String exportDir = "C:/Documents and Settings/hh/Afbeeldingen/Export";
        when(brandweerConfig.getExportDir()).thenReturn(exportDir);
        when(brandweerConfig.getImagePrefix()).thenReturn("IMG");

        List<Person> persons = mockPersons();
        when(personDAO.all()).thenReturn(persons);
        when(personDAO.deleteAll()).thenReturn(persons.size());

        int count = exportService.exportAndCleanUp();

        // verify
        String expectedCsv = createExpectedCsv();
        verify(fileOperations).copyFile("C:/Documents and Settings/hh/Afbeeldingen/DenHaag/IMG00001.JPG",
                "C:/Documents and Settings/hh/Afbeeldingen/Export/HumanityHouse-DenHaag_00001.jpg");
        verify(fileOperations).copyFile("C:/Documents and Settings/hh/Afbeeldingen/DenHaag/IMG00002.JPG",
                "C:/Documents and Settings/hh/Afbeeldingen/Export/HumanityHouse-DenHaag_00002.jpg");
        verify(fileOperations).copyFile("IMG00003.JPG",
                "C:/Documents and Settings/hh/Afbeeldingen/Export/HumanityHouse-00003.jpg");
        verify(fileOperations).copyFile("IMG00004.JPG",
                "C:/Documents and Settings/hh/Afbeeldingen/Export/HumanityHouse-00004.jpg");
        verify(fileOperations).write(exportDir + "/emailadressen.csv", expectedCsv);
        verify(personDAO).deleteAll();
        assertEquals(persons.size(), count);
    }

    private String createExpectedCsv() {
        String expectedCsv = "";
        expectedCsv += "ID,Naam,Email,Foto\r\n";
        expectedCsv += "1,Chris,chris@hh.com,HumanityHouse-DenHaag_00001.jpg\r\n";
        expectedCsv += "2,\"Ditmar van Dam\",ditmar@hh.com,HumanityHouse-DenHaag_00002.jpg\r\n";
        expectedCsv += "3,\"Enfant Terrible\",et@mail.com,HumanityHouse-00003.jpg\r\n";
        expectedCsv += "4,,,HumanityHouse-00004.jpg\r\n";
        return expectedCsv;
    }

    private List<Person> mockPersons() {
        Person chris = mockPerson(1L, "Chris", "chris@hh.com", new Photo(
                "C:\\Documents and Settings\\hh\\Afbeeldingen\\DenHaag\\IMG00001.JPG"));
        Person ditmar = mockPerson(2L, "Ditmar van Dam", "ditmar@hh.com", new Photo(
                "C:\\Documents and Settings\\hh\\Afbeeldingen\\DenHaag\\IMG00002.JPG"));
        Person et = mockPerson(3L, "Enfant Terrible", "et@mail.com", new Photo("IMG00003.JPG"));
        Person nul = mockPerson(4L, null, null, new Photo("IMG00004.JPG"));
        return Arrays.asList(chris, ditmar, et, nul);
    }

    private Person mockPerson(Long id, String name, String email, Photo photo) {
        Person person = mock(Person.class);
        when(person.id()).thenReturn(id);
        when(person.name()).thenReturn(name);
        when(person.email()).thenReturn(email);
        when(person.photo()).thenReturn(photo);
        return person;
    }

}
