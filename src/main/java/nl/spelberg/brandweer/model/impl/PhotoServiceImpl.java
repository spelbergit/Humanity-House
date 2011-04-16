package nl.spelberg.brandweer.model.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import nl.spelberg.brandweer.model.BrandweerConfig;
import nl.spelberg.brandweer.model.Photo;
import nl.spelberg.brandweer.model.PhotoCache;
import nl.spelberg.brandweer.model.PhotoService;
import nl.spelberg.util.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("photoService")
public class PhotoServiceImpl implements PhotoService {

    private static final Log log = LogFactory.getLog(PhotoServiceImpl.class);

    @Autowired
    private PhotoCache photoCache;

    @Autowired
    private BrandweerConfig brandweerConfig;

    @Override
    public Photo findMostRecentPhoto() {
        Set<String> fileNames = Utils.getFileNames(brandweerConfig.getFotoDir(), brandweerConfig.getFotoExtensions());
        if (fileNames.isEmpty()) {
            throw new IllegalStateException(
                    "No files with extension " + Arrays.asList(brandweerConfig.getFotoExtensions()) +
                            " found in dir '" + brandweerConfig.getFotoDir() + "'");
        }

        SortedSet<Photo> photos = new TreeSet<Photo>(
                new Comparator<Photo>() {
                    @Override
                    public int compare(Photo photo, Photo other) {
                        return other.lastModified().compareTo(photo.lastModified());
                    }
                });

        for (String fileName : fileNames) {
            Photo photo = new Photo(brandweerConfig.getFotoDir() + File.separator + fileName);
            photos.add(photo);
        }
        Photo photo = photos.iterator().next();

        log.info("Most recent photo: " + photo);
        return photo;
    }

    @Override
    public byte[] readFotoData(Photo photo) {
        try {
            byte[] data = photoCache.getCachedData(photo);
            if (data == null) {
                data = Utils.getBytes(photo.path());
                photoCache.updateCachedData(photo, data);
            }
            return data;
        } catch (IOException e) {
            throw new RuntimeException("Unable to load photo data for " + photo + "; message: " + e.getMessage(), e);
        }
    }
}
