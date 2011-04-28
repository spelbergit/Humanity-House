package nl.spelberg.brandweer;

import nl.spelberg.brandweer.model.Photo;
import nl.spelberg.brandweer.model.PhotoCache;
import org.apache.wicket.extensions.markup.html.image.resource.ThumbnailImageResource;
import org.springframework.util.Assert;

public class PersonThumbnailImageResource extends ThumbnailImageResource {

    private final PersonDynamicImageResource personDynamicImageResource;

    private final PhotoCache photoCache;

    private final int maxSize;

    public PersonThumbnailImageResource(
            PersonDynamicImageResource personDynamicImageResource, int maxSize, PhotoCache photoCache) {
        super(personDynamicImageResource, maxSize);
        Assert.notNull(photoCache, "photoCache is null");
        Assert.notNull(personDynamicImageResource, "personDynamicImageResource is null");
        this.personDynamicImageResource = personDynamicImageResource;
        this.photoCache = photoCache;
        this.maxSize = maxSize;
    }

    @Override
    protected byte[] getImageData() {

        Photo photo = personDynamicImageResource.getPhoto();
        byte[] data = photoCache.getCachedThumbnailData(photo, maxSize);
        if (data == null) {
            data = super.getImageData();
            photoCache.updateCachedThumbnailData(photo, maxSize, data);
        }
        return data;

    }
}
