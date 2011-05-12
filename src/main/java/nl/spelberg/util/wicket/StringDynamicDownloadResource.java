package nl.spelberg.util.wicket;

import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.StringResourceStream;
import org.springframework.util.Assert;

/**
 * Resource used for dynamic download.
 */
public abstract class StringDynamicDownloadResource extends AbstractDynamicDownloadResource {

    private final ContentType contentType;

    public StringDynamicDownloadResource(String fileName, ContentType contentType) {
        super(fileName);
        Assert.notNull(contentType, "contentType is null");
        this.contentType = contentType;
    }

    @Override
    public final IResourceStream createResourceStream() {
        return new StringResourceStream(loadContent(), contentType.value());
    }

    /**
     * Will be called when the content is accessed.
     *
     * @return The content.
     */
    public abstract String loadContent();
}
