package nl.spelberg.brandweer.dao;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import nl.spelberg.util.ReflectionUtils;

public abstract class AbstractJPADAO<ID extends Serializable, T> implements JPADAO<ID, T> {

    @PersistenceContext
    private EntityManager em;

    private final Class<T> entityClass;

    @SuppressWarnings({"unchecked"})
    protected AbstractJPADAO() {
        this.entityClass = ReflectionUtils.getTypeArgument(AbstractJPADAO.class, this.getClass(), 1);
    }

    @Override
    public final T get(ID id) {
        T entity = find(id);
        if (entity == null) {
            throw new NoResultException("No " + entityClass.getSimpleName() + " found with id='" + id + "'");
        }
        return entity;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    @Override
    public final T find(ID id) {
        return em.find(entityClass, id);
    }

    @Override
    public final void persist(T entity) {
        em.persist(entity);
    }

    @Override
    public final void delete(T entity) {
        em.remove(entity);
    }

    protected final Query namedQuery(String queryName) {
        return em.createNamedQuery(queryName);
    }

    protected final TypedQuery<T> namedTypedQuery(String queryName) {
        return em.createNamedQuery(queryName, entityClass);
    }
}
