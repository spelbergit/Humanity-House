package nl.spelberg.brandweer.model;

public interface FileOperations {

    void copyFile(String fromPath, String toPath);

    String read(String fileName);

    void write(String fileName, String expectedCsv);
}
