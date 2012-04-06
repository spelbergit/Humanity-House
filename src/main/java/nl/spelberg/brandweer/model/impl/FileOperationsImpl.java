package nl.spelberg.brandweer.model.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import nl.spelberg.brandweer.model.FileOperations;
import nl.spelberg.util.Utils;
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

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void createDirectoryRecursive(File path) {
        path.mkdirs();
        if (!path.isDirectory()) {
            throw new FileOperationException("Failed to create directory: " + path.getAbsolutePath());
        }
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    @Override
    public void copyFile(String fromPath, String toPath, FileOperations.CopyOption... copyOptions) {
        try {
            File toFile = new File(toPath);
            if (copyFileNeeded(toFile, copyOptions)) {
                log.debug("Copying file from '" + fromPath + "' to '" + toFile.getAbsolutePath() + "' ...");
                FileInputStream fis = new FileInputStream(fromPath);
                try {
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
            }
        } catch (IOException e) {
            throw new FileOperationException(e);
        }
    }

    private boolean copyFileNeeded(File toFile, CopyOption... copyOptions) {
        if (toFile.isFile() && Utils.contains(copyOptions, CopyOption.SKIP_EXISTING)) {
            return false;
        } else {
            return true;
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
