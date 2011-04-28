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
    public byte[] getCachedThumbnailData(Photo photo, int maxSize) {
        return thumbnailCache.get(createThumbnailPhotoName(photo, maxSize));
    }

    @Override
    public void updateCachedThumbnailData(Photo photo, int maxSize, byte[] data) {
        thumbnailCache.put(createThumbnailPhotoName(photo, maxSize), data);
    }

    private String createThumbnailPhotoName(Photo photo, int maxSize) {
        return photo.name() + "-" + maxSize;
    }
}
