package nl.spelberg.brandweer;

import org.wicketrad.jpa.DataWebApplication;

public class App extends DataWebApplication {

    @Override
    public String getDefaultPersistenceUnitName() {
        return "manager1";
    }

    @Override
    public Class getHomePage() {
        return MyListPage.class;
    }
}