package nl.spelberg.util.wicket;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.target.resource.ResourceStreamRequestTarget;
import org.apache.wicket.util.resource.IResourceStream;

/**
 * Download link that retrieves its content dynamically.
 */
public class DynamicDownloadLink extends Link<DynamicDownloadResource> {

    private static final long serialVersionUID = 1L;

    public DynamicDownloadLink(String id, DynamicDownloadResource dynamicDownloadResource) {
        this(id, new Model<DynamicDownloadResource>(dynamicDownloadResource));
    }

    public DynamicDownloadLink(String id, IModel<DynamicDownloadResource> dynamicDownloadResourceModel) {
        super(id, dynamicDownloadResourceModel);
    }

    /**
     * @see org.apache.wicket.markup.html.link.Link#onClick()
     */
    @Override
    public void onClick() {
        DynamicDownloadResource dynamicDownloadResource = getModelObject();
        IResourceStream resourceStream = dynamicDownloadResource.createResourceStream();
        String fileName = dynamicDownloadResource.getFileName();
        getRequestCycle().setRequestTarget(new ResourceStreamRequestTarget(resourceStream, fileName));
    }

}
