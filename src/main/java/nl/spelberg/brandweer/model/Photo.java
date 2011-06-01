package nl.spelberg.brandweer.model;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Embeddable
public class Photo {

    @Column(name = "PHOTO_PATH")
    private String path;

    @Column(name = "PHOTO_NAME", unique = true)
    private String name;

    @Column(name = "PHOTO_TYPE")
    private String type;

    @Column(name = "PHOTO_LASTMODIFIED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;

    @Deprecated
    protected Photo() {
    }

    public Photo(String path) {
        path = StringUtils.cleanPath(path);

        String filenameExt = StringUtils.getFilename(path);
        String ext = StringUtils.getFilenameExtension(filenameExt);
        Assert.notNull(ext, "Foto heeft geen extensie (jpg, png, gif): '" + path + "'");
        ext = ext.toLowerCase();
        Assert.isTrue(Arrays.asList("jpg", "png", "gif").contains(ext),
                "Foto heeft geen valide extensie (jpg, png, gif): '" + path + "'");
        File file = new File(path);

        this.path = path;
        this.name = StringUtils.stripFilenameExtension(filenameExt);
        this.type = ext;
        this.lastModified = new Date(file.lastModified());
    }

    public String path() {
        return path;
    }

    public String name() {
        return name;
    }

    public String type() {
        return type;
    }

    public Date lastModified() {
        return lastModified;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Photo");
        sb.append("{name='").append(name).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", lastModified=").append(lastModified);
        sb.append(", path='").append(path).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String asHumanityHouseName(String imagePrefix, String imagePrefixReplacement) {
        String fotoNumber = stripPrefix(name(), imagePrefix);
        return imagePrefixReplacement + fotoNumber + "." + type();
    }

    private String stripPrefix(String s, String prefix) {
        if (s.startsWith(prefix)) {
            return s.substring(prefix.length());
        } else {
            return s;
        }
    }
}
