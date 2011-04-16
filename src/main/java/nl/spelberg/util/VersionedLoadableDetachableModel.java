package nl.spelberg.util;

import org.apache.wicket.model.LoadableDetachableModel;

/**
 * Loadable Detachable Model that stores the version. Every time the model is loaded the version number is checkec
 * and when not equal an {@link IllegalStateException} is thrown.
 */
public abstract class VersionedLoadableDetachableModel<T extends Versioned> extends LoadableDetachableModel<T> {

    private Long version;

    /**
     * Constructor.
     */
    protected VersionedLoadableDetachableModel() {
    }

    /**
     * This constructor is used if you already have the object retrieved and want to wrap it with a
     * detachable model.
     *
     * @param versioned retrieved instance of the detachable object
     * @see LoadableDetachableModel#LoadableDetachableModel(Object)
     */
    protected VersionedLoadableDetachableModel(T versioned) {
        super(versioned);
        setVersion(versioned);
    }

    private void setVersion(T versioned) {
        Long versionedVersion = versioned.getVersion();
        if (versionedVersion == null) {
            throw new IllegalStateException(
                    "Versioned instance returned by loadVersioned() has a <null> version; versioned:'" + versioned +
                            "'");
        }
        version = versionedVersion;
    }

    /**
     * Loads the model object using {@link #loadVersioned()} and checks if the {@link nl.spelberg.util.Versioned#getVersion() version} is still the same.
     *
     * @return The model object.
     * @throws IllegalStateException when the loaded model object has a different {@link nl.spelberg.util.Versioned#getVersion() version number} compared with the first
     *                               call to {@link #loadVersioned()}, or when the model object has a <code>null</code> {@link nl.spelberg.util.Versioned#getVersion() version}.
     * @see #loadVersioned()
     * @see Versioned
     * @see Versioned#getVersion()
     * @see LoadableDetachableModel
     */
    @Override
    protected final T load() throws IllegalStateException {
        T versioned = loadVersioned();
        if (versioned != null) {
            Long versionedVersion = versioned.getVersion();
            if (this.version == null) {
                // first time
                setVersion(versioned);
            } else {
                if (!this.version.equals(versionedVersion)) {
                    throw new IllegalStateException(
                            "Versioned instance returned by loadVersioned() has version " + versionedVersion +
                                    ", but earlier used version is " + this.version + "; versioned:'" + versioned +
                                    "'");
                }
            }
        }
        return versioned;
    }

    /**
     * Implement this method to load the actual model object instance.
     *
     * @return The model object.
     */
    protected abstract T loadVersioned();
}
