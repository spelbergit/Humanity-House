package nl.spelberg.csv;

import java.io.Writer;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CSVWriterTest {

    @Test
    public void testAddLine() throws Exception {
        Writer writer = mock(Writer.class, RETURNS_SMART_NULLS);

        // execute
        CSVWriter csvWriter = new CSVWriter(writer);
        csvWriter.addLine("one", "two", "three");
        csvWriter.addLine("two", "three", "four");
        csvWriter.addLine("three", "four", "five");
        csvWriter.addLine("four three", "four", "five");
        csvWriter.addLine("five \"four\" three", "four", "five");

        // verify
        verify(writer).append("one,two,three\r\n");
        verify(writer).append("two,three,four\r\n");
        verify(writer).append("three,four,five\r\n");
        verify(writer).append("\"four three\",four,five\r\n");
        verify(writer).append("\"five \"\"\"four\"\"\" three\",four,five\r\n");
    }

    @Test
    public void testAddLineError() throws Exception {
        Writer writer = mock(Writer.class, RETURNS_SMART_NULLS);

        // execute
        CSVWriter csvWriter = new CSVWriter(writer);
        csvWriter.addLine("one", "two", "three");

        assertEquals(3, csvWriter.columns());

        try {
            csvWriter.addLine("two", "three");
            fail();
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    @Test
    public void testAddLineNothing() throws Exception {
        Writer writer = mock(Writer.class, RETURNS_SMART_NULLS);

        // execute
        CSVWriter csvWriter = new CSVWriter(writer);

        try {
            csvWriter.addLine();
            fail();
        } catch (IllegalArgumentException e) {
            // success
        }
    }

}
