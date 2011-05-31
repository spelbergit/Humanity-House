package nl.spelberg.brandweer.model;

public interface ConfigService {

    BrandweerConfig getConfig();

    void updateConfig(BrandweerConfig brandweerConfig);
}
