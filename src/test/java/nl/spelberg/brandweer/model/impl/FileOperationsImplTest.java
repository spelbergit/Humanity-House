package nl.spelberg.brandweer.model.impl;

import java.io.File;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FileOperationsImplTest {

    public static final String TEST_IMPORT_FILE = "src/test/resources/test-import-file.csv";
    private FileOperationsImpl fileOperations;

    @Before
    public void setUp() throws Exception {
        fileOperations = new FileOperationsImpl();
    }

    @Test
    public void testCopyFile() throws IOException {
        // setup
        String toPath = "target/" + getClass().getName() + "/test-import-file-copy.csv";

        // execute
        fileOperations.copyFile(TEST_IMPORT_FILE, toPath);

        // verify
        File copiedFile = new File(toPath);
        assertEquals("File 'src/test/resources/test-import-file.csv' should have been copied to '" + toPath + "'", true,
                copiedFile.isFile());


        boolean deleted = copiedFile.delete();
        assertTrue(deleted);

    }


    @Test
    public void testRead() {

        // execute
        String result = fileOperations.read(TEST_IMPORT_FILE);

        // verify
        assertEquals("Name;Email;Photo Filename\nLoetie Kruger;loetie@driebit.nl;smile-yellow.jpg", result);
    }

}
