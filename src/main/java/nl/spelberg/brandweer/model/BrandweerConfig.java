package nl.spelberg.brandweer.model;

import org.springframework.beans.factory.annotation.Required;

public class BrandweerConfig {

    private static final String[] PHOTO_EXTENSIONS = {"jpg", "png", "gif"};

    private String fotoDir;

    public String getFotoDir() {
        return fotoDir;
    }

    @Required
    public void setFotoDir(String fotoDir) {
        this.fotoDir = fotoDir;
    }

    public String[] getFotoExtensions() {
        return PHOTO_EXTENSIONS;
    }
}
