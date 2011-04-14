package nl.spelberg.brandweer;

import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.stereotype.Component;

@Component
public class App extends WebApplication {

    @Override
    public Class<HomePage> getHomePage() {
        return HomePage.class;
    }

    @Override
    protected void init() {
        mountBookmarkablePage("enter", EnterDetailsPage.class);
    }
}