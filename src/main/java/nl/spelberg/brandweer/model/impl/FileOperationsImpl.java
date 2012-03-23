package nl.spelberg.brandweer.model.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import nl.spelberg.brandweer.model.FileOperations;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class FileOperationsImpl implements FileOperations {

    private static final Log log = LogFactory.getLog(FileOperationsImpl.class);

    @Override
    public void createDirectoryRecursive(String dirPath) {
        createDirectoryRecursive(new File(dirPath));
    }

    public void createDirectoryRecursive(File path) {
        boolean result = path.mkdirs();
        if (result == false) {
            throw new FileOperationException("Failed to create directory: " + path.getAbsolutePath());
        }
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    @Override
    public void copyFile(String fromPath, String toPath) {
        try {
            FileInputStream fis = new FileInputStream(fromPath);
            try {
                File toFile = new File(toPath);
                toFile.getParentFile().mkdirs();
                FileOutputStream fos = new FileOutputStream(toFile);
                try {

                    byte[] bytes = new byte[8192];
                    int n;
                    while ((n = fis.read(bytes)) >= 0) {
                        fos.write(bytes, 0, n);
                    }
                    fos.flush();

                } finally {
                    fos.close();
                }
            } finally {
                fis.close();
            }
        } catch (IOException e) {
            throw new FileOperationException(e);
        }
    }

    @Override
    public String read(String fileName) {
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            StringBuilder sb = null;
            String line;
            while ((line = br.readLine()) != null) {
                if (sb == null) {
                    sb = new StringBuilder();
                } else {
                    sb.append("\n");
                }
                sb.append(line);
            }
            return sb == null ? "" : sb.toString();
        } catch (IOException e) {
            throw new FileOperationException(e);
        }
    }

    @Override
    public void write(String fileName, String expectedCsv) {
        try {
            File file = new File(fileName);
            //noinspection ResultOfMethodCallIgnored
            file.getParentFile().mkdirs();
            FileWriter fw = new FileWriter(file);
            try {
                fw.write(expectedCsv);
            } finally {
                fw.close();
            }
        } catch (IOException e) {
            throw new FileOperationException(e);
        }
    }

}
