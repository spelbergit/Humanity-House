package nl.spelberg.brandweer.model.impl;

import java.io.IOException;

public class FileOperationException extends RuntimeException {

    public FileOperationException(IOException e) {
        super(e.getMessage(), e);
    }

}
