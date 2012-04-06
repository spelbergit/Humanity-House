package nl.spelberg.brandweer.model.impl;

import java.io.File;
import java.io.FileNotFoundException;

public class NotFoundFileOperationException extends FileOperationException {
    private final String filePath;

    protected NotFoundFileOperationException(String filePath) {
        super("Not Found: '" + filePath + "'");
        this.filePath = filePath;
    }

    public NotFoundFileOperationException(File file) {
        this(absoluteFilePath(file));
    }

    public NotFoundFileOperationException(FileNotFoundException e, File file) {
        super("Not Found: '" + absoluteFilePath(file) + "': " + e.getMessage(), e);
        this.filePath = absoluteFilePath(file);
    }

    public final String filePath() {
        return filePath;
    }

    private static String absoluteFilePath(File file) {
        return file == null ? "<null>" : file.getAbsolutePath();
    }

}
