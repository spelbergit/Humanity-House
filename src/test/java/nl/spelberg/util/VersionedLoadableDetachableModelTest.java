package nl.spelberg.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VersionedLoadableDetachableModelTest {

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private VersionedFactory versionedFactory;


    @Test
    public void testFirstTime() {
        Versioned versioned = mock(Versioned.class, RETURNS_SMART_NULLS);
        when(versionedFactory.create()).thenReturn(versioned);
        when(versioned.getVersion()).thenReturn(1L);

        VersionedLoadableDetachableModel vldm = new MyVersionedLoadableDetachableModel(versionedFactory);

        assertEquals(versioned, vldm.loadVersioned());
        assertEquals(versioned, vldm.load());
        assertEquals(versioned, vldm.load());
        assertEquals(versioned, vldm.load());
        assertEquals(versioned, vldm.load());
        assertEquals(versioned, vldm.load());

        verify(versionedFactory, atLeastOnce()).create();
        verify(versioned, atLeastOnce()).getVersion();
    }

    @Test
    public void testNoVersionTime() {
        Versioned versioned = mock(Versioned.class, RETURNS_SMART_NULLS);
        when(versionedFactory.create()).thenReturn(versioned);
        when(versioned.getVersion()).thenReturn(null);
        VersionedLoadableDetachableModel vldm = new MyVersionedLoadableDetachableModel(versionedFactory);

        assertEquals(versioned, vldm.loadVersioned());
        try {
            vldm.load();
            fail();
        } catch (IllegalStateException e) {
            // ok
            assertTrue(
                    "\nexpected: Versioned instance returned by loadVersioned() has a <null> version;" +
                            "\n but was: " + e.getMessage(), e.getMessage().startsWith(
                    "Versioned instance returned by loadVersioned() has a <null> version;"));
        }


        verify(versionedFactory, atLeastOnce()).create();
        verify(versioned, atLeastOnce()).getVersion();
    }

    @Test
    public void testNewerVersionTime() {
        // test version 1
        Versioned versioned = mock(Versioned.class, RETURNS_SMART_NULLS);
        when(versioned.getVersion()).thenReturn(1L);
        when(versionedFactory.create()).thenReturn(versioned);
        VersionedLoadableDetachableModel vldm = new MyVersionedLoadableDetachableModel(versionedFactory);

        assertEquals(versioned, vldm.load());

        // test newer version
        Versioned versioned2 = mock(Versioned.class, RETURNS_SMART_NULLS);
        when(versioned2.getVersion()).thenReturn(2L);
        when(versionedFactory.create()).thenReturn(versioned2);

        // execute
        try {
            vldm.load();
            fail();
        } catch (IllegalStateException e) {
            // ok
            assertTrue(
                    e.getMessage().startsWith(
                            "Versioned instance returned by loadVersioned() has version 2, but earlier used version is 1;"));
        }

        // verify
        verify(versionedFactory, atLeastOnce()).create();
        verify(versioned, atLeastOnce()).getVersion();
    }

    public static class MyVersionedLoadableDetachableModel extends VersionedLoadableDetachableModel<Versioned> {

        private final VersionedFactory versionedFactory;

        public MyVersionedLoadableDetachableModel(VersionedFactory versionedFactory) {
            this.versionedFactory = versionedFactory;
        }

        @Override
        protected Versioned loadVersioned() {
            return versionedFactory.create();
        }
    }

    public static interface VersionedFactory {
        Versioned create();
    }
}
