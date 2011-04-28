package nl.spelberg.brandweer;

import org.apache.wicket.markup.html.AjaxServerAndClientTimeFilter;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.stereotype.Component;

@Component
public class App extends WebApplication {

    @Override
    public Class<HomePage> getHomePage() {
        return HomePage.class;
    }

    @Override
    protected void init() {
        // spring-wicket binding
        addComponentInstantiationListener(new SpringComponentInjector(this));

        // ajax
        getRequestCycleSettings().addResponseFilter(new AjaxServerAndClientTimeFilter());
        getDebugSettings().setAjaxDebugModeEnabled(false);

        // nice url's
        mountBookmarkablePage("enter", EnterDetailsPage.class);
    }
}