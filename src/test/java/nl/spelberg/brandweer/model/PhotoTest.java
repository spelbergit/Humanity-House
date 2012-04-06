package nl.spelberg.brandweer.model;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class PhotoTest {

    @Test
    public void testCreate1() {
        Photo photo = new Photo("/tmp/fotos/IMG0001.jpg");

        assertEquals("/tmp/fotos/IMG0001.jpg", photo.path());
        assertEquals("IMG0001", photo.name());
        assertEquals("jpg", photo.type());
    }

    @Test
    public void testCreate2() {
        Photo photo = new Photo("/tmp/fotos/IMG0002.JPG");

        assertEquals("/tmp/fotos/IMG0002.JPG", photo.path());
        assertEquals("IMG0002", photo.name());
        assertEquals("jpg", photo.type());
    }

    @Test
    public void testCreate3() {
        Photo photo = new Photo("/tmp/fotos/IMG0003.png");

        assertEquals("/tmp/fotos/IMG0003.png", photo.path());
        assertEquals("IMG0003", photo.name());
        assertEquals("png", photo.type());
    }

    @Test
    public void testHumanityHouseName() throws IOException {
        // setup
        File photoFile = createTempPhoto();
        Photo photo = new Photo(photoFile.getAbsolutePath());
        String photoNumber = StringUtils.removeEnd(photoFile.getName().substring(3), ".jpg");

        String photoDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(photoFile.lastModified()));
        assertEquals("Blabla-" + photoDate + "-" + photoNumber + ".jpg", photo.asHumanityHouseName("IMG", "Blabla"));
    }

    private File createTempPhoto() throws IOException {
        File photoFile = File.createTempFile("IMG", ".jpg");
        photoFile.deleteOnExit();
        return photoFile;
    }
}
