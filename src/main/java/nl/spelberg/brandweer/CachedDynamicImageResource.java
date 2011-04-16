package nl.spelberg.brandweer;

import nl.spelberg.util.WeakByteArrayCache;
import org.apache.wicket.markup.html.image.resource.DynamicImageResource;
import org.springframework.util.Assert;

public abstract class CachedDynamicImageResource extends DynamicImageResource {

    private WeakByteArrayCache cache = new WeakByteArrayCache();

    private final String key;

    public CachedDynamicImageResource(String key) {
        Assert.notNull(key, "key is null");
        this.key = key;
    }

    public final String key() {
        return key;
    }

    @Override
    protected final byte[] getImageData() {
        byte[] bytes = cache.getCachedData(key);
        if (bytes == null) {
            bytes = loadImageData();
            cache.updateCachedData(key, bytes);
        }
        return bytes;
    }

    protected abstract byte[] loadImageData();

}
