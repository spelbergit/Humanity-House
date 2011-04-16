package nl.spelberg.brandweer.model;

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

}
