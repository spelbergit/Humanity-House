package nl.spelberg.brandweer.dao;

import org.junit.Test;
import static org.junit.Assert.*;

public class AbstractJPADAOTest {

    @Test
    public void testEntityClass() {
        AbstractJPADAO<Long, String> jpadao = new AbstractJPADAO<Long, String>() {
        };
        assertEquals(String.class, jpadao.getEntityClass());
    }

}
