package nl.spelberg.brandweer.model.impl;

import nl.spelberg.brandweer.model.BrandweerConfig;
import nl.spelberg.brandweer.model.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component("configService")
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    BrandweerConfig brandweerConfigFromPropertyFile;

    @Override
    public BrandweerConfig getConfig() {
        Assert.notNull(brandweerConfigFromPropertyFile);
        return brandweerConfigFromPropertyFile;
    }

    @Override
    public void updateConfig(BrandweerConfig brandweerConfig) {
        throw new UnsupportedOperationException(
                this.getClass().getSimpleName() + ".updateConfig(BrandweerConfig brandweerConfig)");
    }

}
