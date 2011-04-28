package nl.spelberg.brandweer.model;

public interface PersonService {

    Person findPerson(Long id);

    Person getMostRecentPerson();

    /**
     * Checks if a new photo is detected in the photo dir.
     *
     * @return <code>true</code> when a new photo existe in the photo dir that does not have a record in the database table.
     */
    boolean hasNewPerson();

    void updatePerson(Person person);
}
