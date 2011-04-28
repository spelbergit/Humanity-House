package nl.spelberg.brandweer.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.StringUtils;

public class BrandweerConfig {

    private static final Log log = LogFactory.getLog(BrandweerConfig.class);

    private static final String[] PHOTO_EXTENSIONS = {"jpg", "png", "gif"};

    private String fotoDir;

    private int maxPhotoSize;
    private String logLevel;

    public String getFotoDir() {
        return fotoDir;
    }

    @Required
    public void setFotoDir(String fotoDir) {
        this.fotoDir = StringUtils.cleanPath(fotoDir);
    }

    public int getMaxPhotoSize() {
        return maxPhotoSize;
    }

    @Required
    public void setMaxPhotoSize(int maxPhotoSize) {
        this.maxPhotoSize = maxPhotoSize;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }


    public String getLogLevel() {
        return logLevel;
    }

    public String[] getFotoExtensions() {
        return PHOTO_EXTENSIONS;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("BrandweerConfig");
        sb.append("{fotoDir='").append(fotoDir).append('\'');
        sb.append(", maxPhotoSize=").append(maxPhotoSize);
        sb.append('}');
        return sb.toString();
    }
}
