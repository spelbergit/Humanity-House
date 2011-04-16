package nl.spelberg.brandweer.model;

public interface PhotoCache {

    byte[] getCachedData(Photo photo);

    void updateCachedData(Photo photo, byte[] data);
}
