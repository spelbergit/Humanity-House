package nl.spelberg.brandweer.model;

public interface FileOperations {

    void copyFile(String fromPath, String toPath, FileOperations.CopyOption... copyOptions);

    String read(String fileName);

    void write(String fileName, String expectedCsv);

    void createDirectoryRecursive(String dirPath);

    void deleteFile(String fileName);

    void moveFileToDir(String filePath, String toDir);

    boolean fileExists(String filePath);

    enum CopyOption {
        SKIP_EXISTING
    }
}
