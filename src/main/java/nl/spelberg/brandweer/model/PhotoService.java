package nl.spelberg.brandweer.model;

public interface PhotoService {

    /**
     * Finds the most recent added photo in the photo dir.
     *
     * @return the photo, or <code>null</code> when the photo dir does not contain any photos.
     */
    Photo findMostRecentPhoto();

    byte[] readFotoData(Photo photo);

    byte[] readFotoThumbnailData(Photo photo);
}
