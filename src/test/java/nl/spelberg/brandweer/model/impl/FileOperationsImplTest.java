package nl.spelberg.brandweer.model.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FileOperationsImplTest {

    public static final String OUTPUT_DIR = "target/" + FileOperationsImplTest.class.getName();
    public static final String TEST_IMPORT_FILE = "src/test/resources/test-import-file.csv";
    private FileOperationsImpl fileOperations;

    @Before
    public void setUp() throws Exception {
        fileOperations = new FileOperationsImpl();
    }

    @Test
    public void testCopyFile() throws IOException {
        // setup
        String toPath = OUTPUT_DIR + "/test-import-file-copy.csv";

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

    @Test
    public void testWrite() throws IOException {
        // setup
        String subdir = OUTPUT_DIR + "/subdir";
        String fileName = subdir + "/testWrite.txt";

        // make sure a previous output file is deleted before starting the test
        // normally it will be deleted by 'maven clean'
        File previousFile = new File(fileName);
        File subdirFile = new File(subdir);
        if (previousFile.exists()) {
            boolean previousFileDeleted = previousFile.delete();
            assertTrue(previousFileDeleted);
            if (subdirFile.exists()) {
                boolean subdirDeleted = subdirFile.delete();
                assertTrue(subdirDeleted);
            }
        }
        assertFalse(previousFile.exists());
        assertFalse(subdirFile.exists());

        String expectedContent = "Dummy text to test write stuff.";

        // execute
        fileOperations.write(fileName, expectedContent);

        // verify
        File resultFile = new File(fileName);
        assertTrue(resultFile.isFile());

        StringBuilder sb = new StringBuilder();
        FileReader fileReader = new FileReader(fileName);
        int i;
        while ((i = fileReader.read()) >= 0) {
            char c = (char) i;
            sb.append(c);
        }
        assertEquals(expectedContent, sb.toString());

    }

}
