package nl.spelberg.brandweer.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import nl.spelberg.brandweer.model.Person;
import nl.spelberg.brandweer.model.Photo;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDAOImpl extends AbstractJPADAO<Long, Person> implements PersonDAO {

    @Override
    public Person getMostRecentPerson() {
        TypedQuery<Person> query = em().createNamedQuery("Person.findMostRecent", Person.class);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    @Override
    public Person find(Photo photo) {
        try {
            TypedQuery<Person> query = em().createNamedQuery("Person.findByPhoto", Person.class);
            query.setParameter("photoName", photo.name());
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Person> all() {
        TypedQuery<Person> query = em().createNamedQuery("Person.all", Person.class);
        return query.getResultList();
    }

    @Override
    public Long count() {
        TypedQuery<Long> query = em().createNamedQuery("Person.count", Long.class);
        return query.getSingleResult();
    }

    @Override
    public int deleteAll() {
        Query query = em().createNamedQuery("Person.deleteAll");
        return query.executeUpdate();
    }

}
