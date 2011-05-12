package nl.spelberg.brandweer.dao;

import java.util.List;
import nl.spelberg.brandweer.model.Person;
import nl.spelberg.brandweer.model.Photo;

public interface PersonDAO extends JPADAO<Long, Person> {

    Person getMostRecentPerson();

    Person find(Photo photo);

    List<Person> all();

    Long count();
}
