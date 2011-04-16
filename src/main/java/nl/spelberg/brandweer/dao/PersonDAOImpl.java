package nl.spelberg.brandweer.dao;

import nl.spelberg.brandweer.model.Person;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDAOImpl extends AbstractJPADAO<Long, Person> implements PersonDAO {

}
