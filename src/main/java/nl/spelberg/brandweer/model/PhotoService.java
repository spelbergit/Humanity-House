package nl.spelberg.brandweer.model;

public interface PhotoService {

    /**
     * Finds the most recent added photo in the photo dir.
     *
     * @return the photo
     */
    Photo findMostRecentPhoto();

    byte[] readFotoData(Photo photo);

    byte[] readFotoThumbnailData(Photo photo);

}
