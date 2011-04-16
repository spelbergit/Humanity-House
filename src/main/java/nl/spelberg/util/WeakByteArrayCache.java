package nl.spelberg.util;

import java.util.Map;
import java.util.WeakHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WeakByteArrayCache {

    private static final Log log = LogFactory.getLog(WeakByteArrayCache.class);

    private Map<String, byte[]> cache = new WeakHashMap<String, byte[]>();

    public byte[] getCachedData(String key) {
        return cache.get(key);
    }

    public void updateCachedData(String key, byte[] data) {
        cache.put(key, data);
    }
}
