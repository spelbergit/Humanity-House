package nl.spelberg.util.wicket;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.FormComponent;

/**
 * Add this behavior to a component to give it the default focus.
 */
public class DefaultFocusBehavior extends AbstractBehavior {

    private static final long serialVersionUID = -4891399118136854774L;

    private Component component;

    @Override
    public void bind(Component component) {
        if (!(component instanceof FormComponent)) {
            throw new IllegalArgumentException("DefaultFocusBehavior: component must be instanceof FormComponent");
        }
        this.component = component;
        component.setOutputMarkupId(true);
    }

    @Override
    public void renderHead(IHeaderResponse iHeaderResponse) {
        super.renderHead(iHeaderResponse);
        iHeaderResponse.renderOnLoadJavascript("document.getElementById('" + component.getMarkupId() + "').focus();");
    }
}