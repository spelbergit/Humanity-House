package nl.spelberg.util.wicket;

import org.springframework.util.Assert;

public abstract class AbstractDynamicDownloadResource implements DynamicDownloadResource {

    private final String fileName;

    public AbstractDynamicDownloadResource(String fileName) {
        Assert.notNull(fileName, "fileName is null");
        this.fileName = fileName;
    }

    @Override
    public final String getFileName() {
        return fileName;
    }

}
