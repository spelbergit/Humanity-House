package nl.spelberg.brandweer.dao;

import java.io.Serializable;

/**
 * TODO: write comment
 */
public interface JPADAO<ID extends Serializable, T> {
    T get(ID id);

    T find(ID id);

    void persist(T entity);

    void delete(T entity);
}
