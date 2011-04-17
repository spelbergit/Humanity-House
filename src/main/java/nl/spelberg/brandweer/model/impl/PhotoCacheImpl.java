package nl.spelberg.brandweer.model.impl;

import java.util.Map;
import java.util.WeakHashMap;
import nl.spelberg.brandweer.model.Photo;
import nl.spelberg.brandweer.model.PhotoCache;
import org.springframework.stereotype.Service;

@Service
public class PhotoCacheImpl implements PhotoCache {

    private Map<String, byte[]> cache = new WeakHashMap<String, byte[]>();
    private Map<String, byte[]> thumbnailCache = new WeakHashMap<String, byte[]>();

    @Override
    public byte[] getCachedData(Photo photo) {
        return cache.get(photo.name());
    }

    @Override
    public void updateCachedData(Photo photo, byte[] data) {
        cache.put(photo.name(), data);
    }

    @Override
    public byte[] getCachedThumbnailData(Photo photo) {
        return thumbnailCache.get(photo.name());
    }

    @Override
    public void updateCachedThumbnailData(Photo photo, byte[] data) {
        thumbnailCache.put(photo.name(), data);
    }
}
