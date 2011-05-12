package nl.spelberg.util.wicket;

import java.io.Serializable;
import org.apache.wicket.util.resource.IResourceStream;

public interface DynamicDownloadResource extends Serializable {

    String getFileName();

    IResourceStream createResourceStream();
}
