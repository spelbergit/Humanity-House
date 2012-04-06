package nl.spelberg.util;

import java.util.Arrays;
import org.junit.Test;

import static org.junit.Assert.*;

public class UtilsTest {
    @Test
    public void testContains() throws Exception {
        assertEquals(false, Utils.contains(null, null));
        assertEquals(false, Utils.contains(new Object[0], null));
        assertEquals(true, Utils.contains(new Object[1], null));
        assertEquals(false, Utils.contains(Arrays.asList("Hello", "World").toArray(), null));
        assertEquals(false, Utils.contains(Arrays.asList("Hello", "World").toArray(), null));
        assertEquals(true, Utils.contains(Arrays.asList("Hello", "World").toArray(), "Hello"));
        assertEquals(true, Utils.contains(Arrays.asList("Hello", "World").toArray(), "World"));
        assertEquals(true, Utils.contains(Arrays.asList("Hello", null, "World").toArray(), "Hello"));
        assertEquals(true, Utils.contains(Arrays.asList("Hello", null, "World").toArray(), null));
        assertEquals(true, Utils.contains(Arrays.asList("Hello", null, "World").toArray(), "World"));
    }
}
