package nl.spelberg.csv;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

public class CSVWriter {

    private final Writer writer;
    private final String separator;
    private final String newLine;
    private final String quote;

    private int columns = -1;

    /**
     * Creates a new CSVWriter.
     *
     * @param writer The writer to write the csv to. Note: the writer must be closed by the caller.
     */
    public CSVWriter(Writer writer) {
        Assert.notNull(writer, "writer is null");
        this.writer = writer;
        this.separator = ",";
        this.newLine = "\r\n";
        this.quote = "\"";
    }

    /**
     * Add a line to the csv file.
     *
     * @param values The values.
     * @throws IOException              when the line can not be written to the writer.
     * @throws IllegalArgumentException when values.length differs from previously added lines.
     */
    public void addLine(String... values) throws IOException, IllegalArgumentException {
        Assert.notNull(values, "values is null");
        Assert.notEmpty(values, "values is empty");
        if (columns == -1) {
            columns = values.length;
        } else {
            if (columns != values.length) {
                throw new IllegalArgumentException(
                        "line should have " + columns + " values but has " + values.length + " columns: " +
                                Arrays.toString(values));
            }
        }

        String[] escapedValues = escape(values);
        String line = StringUtils.join(escapedValues, separator) + newLine;
        writer.append(line);
    }

    private String[] escape(String[] values) {
        String[] escapedValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            escapedValues[i] = escape(values[i]);
        }
        return escapedValues;
    }

    private String escape(String value) {
        if (value.contains(quote)) {
            value = StringUtils.replace(value, quote, quote + quote + quote);
        }
        if (value.contains(" ")) {
            return quote + value + quote;
        } else {
            return value;
        }
    }

    public int columns() {
        return columns;
    }
}
