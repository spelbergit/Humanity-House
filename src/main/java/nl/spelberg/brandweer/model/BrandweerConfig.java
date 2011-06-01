package nl.spelberg.brandweer.model;

import javax.persistence.Id;
import nl.spelberg.util.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.StringUtils;

public class BrandweerConfig {

    private static final Log log = LogFactory.getLog(BrandweerConfig.class);

    private static final String[] PHOTO_EXTENSIONS = {"jpg", "png", "gif"};

    @Id
    private Long id;

    private String fotoDir;
    private String exportDir;

    private String imagePrefix;
    private String imagePrefixReplacement;

    private int maxPhotoSize;
    private int timingHome;
    private int timingEmail;

    private int timingFinish;
    private String logLevel = Level.INFO.toString();

    public BrandweerConfig() {
        // set loglevel of BrandweerConfig always to INFO:
        Logger.getLogger(getClass()).setLevel(Level.INFO);
    }

    public String getFotoDir() {
        return fotoDir;
    }

    @Required
    public void setFotoDir(String fotoDir) {
        this.fotoDir = StringUtils.cleanPath(fotoDir);
        logConfig("fotoDir=" + this.fotoDir);
    }

    public String getExportDir() {
        return exportDir;
    }

    @Required
    public void setExportDir(String exportDir) {
        this.exportDir = StringUtils.cleanPath(exportDir);
        logConfig("exportDir=" + this.exportDir);
    }

    public String getExportDirNative() {
        return Utils.nativePath(exportDir);
    }

    public String getImagePrefix() {
        return imagePrefix;
    }

    @Required
    public void setImagePrefix(String imagePrefix) {
        this.imagePrefix = imagePrefix;
        logConfig("imagePrefix=" + this.imagePrefix);
    }

    public String getImagePrefixReplacement() {
        return imagePrefixReplacement;
    }

    @Required
    public void setImagePrefixReplacement(String imagePrefixReplacement) {
        this.imagePrefixReplacement = imagePrefixReplacement;
        logConfig("imagePrefixReplacement=" + this.imagePrefixReplacement);
    }

    public int getMaxPhotoSize() {
        return maxPhotoSize;
    }

    @Required
    public void setMaxPhotoSize(int maxPhotoSize) {
        this.maxPhotoSize = maxPhotoSize;
        logConfig("maxPhotoSize=" + this.maxPhotoSize);
    }

    public int getTimingHome() {
        return timingHome;
    }

    @Required
    public void setTimingHome(int timingHome) {
        this.timingHome = timingHome;
        logConfig("timingHome=" + this.timingHome);
    }

    public int getTimingEmail() {
        return timingEmail;
    }

    @Required
    public void setTimingEmail(int timingEmail) {
        this.timingEmail = timingEmail;
        logConfig("timingEmail=" + this.timingEmail);
    }

    public int getTimingFinish() {
        return timingFinish;
    }

    @Required
    public void setTimingFinish(int timingFinish) {
        this.timingFinish = timingFinish;
        logConfig("timingFinish=" + this.timingFinish);
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
        // configure log4j
        Logger brandweerLogger = LogManager.getLogger("nl.spelberg");
        brandweerLogger.setLevel(Level.toLevel(logLevel));
        logConfig("Log level set: " + brandweerLogger.getName() + ".level=" + brandweerLogger.getLevel());
    }

    private void logConfig(String message) {
        log.info(message);
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
