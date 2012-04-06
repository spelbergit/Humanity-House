package nl.spelberg.brandweer.model;

public interface FileOperations {

    void copyFile(String fromPath, String toPath, FileOperations.CopyOption... copyOptions);

    String read(String fileName);

    void write(String fileName, String expectedCsv);

    void createDirectoryRecursive(String dirPath);

    enum CopyOption {
        SKIP_EXISTING
    }
}
