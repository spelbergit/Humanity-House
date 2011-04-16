package nl.spelberg.brandweer.model;

public interface PersonService {

    public Person findPerson(Long id);

    public Person getMostRecentPerson();

}
