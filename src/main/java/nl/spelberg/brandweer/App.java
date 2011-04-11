package nl.spelberg.brandweer;

import org.apache.wicket.protocol.http.WebApplication;

public class App extends WebApplication {

    @Override
    public Class<HomePage> getHomePage() {
        return HomePage.class;
    }
}