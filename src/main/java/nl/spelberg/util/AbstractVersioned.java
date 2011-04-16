package nl.spelberg.util;

import javax.persistence.Version;

public class AbstractVersioned implements Versioned {

    @Version
    private Long version = 1L;

    @Override
    public Long getVersion() {
        return version;
    }
}
